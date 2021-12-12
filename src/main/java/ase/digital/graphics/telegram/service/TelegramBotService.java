package ase.digital.graphics.telegram.service;

import ase.digital.graphics.telegram.feign.SecurityFeignClient;
import ase.digital.graphics.telegram.model.Users;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramBotService {
    private final SecurityFeignClient securityFeignClient;

// Добавить очистку файлов
@SneakyThrows
    public BotApiMethod<?> sendAse(Update update) {
    try {
        var users = securityFeignClient.findById(update.getMessage().getFrom().getId());
       log.info("User found "+users);
    } catch (FeignException e) {
        return new SendMessage(String.valueOf(update.getMessage().getChatId()), """
            Не найдена учетная запись с таким id.
            Обратитесь в службу тех.поддержки.
            """);
    }
    try {
        new File("src/main/resources/download/"+update.getMessage().getFrom().getId()).mkdirs();
        uploadFile(update.getMessage().getDocument().getFileName(), update.getMessage().getDocument().getFileId(), update);
        System.out.println(countFiles());
        List<File> filesInFolder = Files.walk(Paths.get("src/main/resources/download/"+update.getMessage().getFrom().getId()))
            .filter(Files::isRegularFile)
            .map(Path::toFile)
            .collect(Collectors.toList());
        if (filesInFolder.size() == 3) {
             //Отправка три файла
            // Получить три файла
            FileUtils.deleteDirectory(new File("src/main/resources/download/" + update.getMessage().getFrom().getId()));
            return new SendMessage(String.valueOf(update.getMessage().getChatId()), """
                Все три файла были отправлены
                """);
        } else return new SendMessage(String.valueOf(update.getMessage().getChatId()), """
            Отправьте еще один файл
            """);
    } catch (NullPointerException e) {
        return new SendMessage(String.valueOf(update.getMessage().getChatId()), """
            Отправлено видео
            """);
    }

}

    @SneakyThrows
    public void uploadFile(String file_name, String file_id, Update update) {
        URL url = new URL("https://api.telegram.org/bot"+"5079932153:AAF0XMzYjJgS2AvYNHs0G-82EGT1jyBCFGQ"+"/getFile?file_id="+file_id);
        BufferedReader in = new BufferedReader(new InputStreamReader( url.openStream()));
        String res = in.readLine();
        JSONObject jresult = new JSONObject(res);
        JSONObject path = jresult.getJSONObject("result");
        String file_path = path.getString("file_path");
        File fileLocal = new File("src/main/resources/download/"+update.getMessage().getFrom().getId()+"/"+file_name);
        InputStream is = new URL("https://api.telegram.org/file/bot" + "5079932153:AAF0XMzYjJgS2AvYNHs0G-82EGT1jyBCFGQ" + "/" + file_path).openStream();
       FileUtils.copyInputStreamToFile(is,fileLocal);
       in.close();
       is.close();
        System.out.println("lol");
    }

    public int countFiles() {
        List<String> fileNames = new ArrayList<>();
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("src/main/resources/download/"));
            for (Path path : directoryStream) {
                fileNames.add(path.toString());
            }
        } catch (IOException ignored) {
        }
       return fileNames.size();
    }
}

