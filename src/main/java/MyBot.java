import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class MyBot extends TelegramLongPollingBot {
    //private static final Logger LOGGER =
      //      Logger.getLogger(MyBot.class.getName());
    //private static final Dao<AlgOrStr, Integer> ALG_OR_STR_DAO = new PostgreSqlDao();
    Statement statement = null;
    ResultSet resultSet = null;
    Connection connection = null;
    SendMessage message = new SendMessage();
    String react;
    boolean isAlg;
    @Override
    public void onUpdateReceived(Update update) {
        try {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/algoriphms_and_structures", "postgres", "mariach2003");
        } catch (SQLException e) {
        throw new RuntimeException(e);
        }
        if (update.hasMessage()&&update.getMessage().hasText()){
            String text = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            message.setChatId(chatId);
            if (text.equals("/start")){
                message.setText("Hello world! \n Выберите пунк меню");
                defKeyboard();
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else{
                String table;
                String mes;
                String col;
                if (isAlg){
                    table = "algoriphms";
                    col = "Alg";
                    mes = "Информации о данном алгоритме нет в базе данных";
                } else{
                    table = "structures";
                    col = "Struct";
                    mes = "Информации о данной структуре данных нет в базе данных";
                }
                String name = update.getMessage().getText();
                int id = getId("SELECT * FROM spr_alg_struc;",name);
                if (id!=0){
                    message.setText(query1("SELECT \""+react+"\" FROM "+table+" WHERE \""+col+"\"="+id+";",name));
                    defKeyboard();
                } else{
                    message.setText(mes);
                    defKeyboard();
                }
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            defKeyboard();
        }
        String b1 = query("SELECT * FROM spr_alg_struc;");
            if (update.hasCallbackQuery()) {
                String chId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
                CallbackQuery callbackQuery = update.getCallbackQuery();
                String callbackData = callbackQuery.getData();
                switch (callbackData) {
                    case "button1" -> {
                        message.setChatId(chId);
                        message.setText(b1);
                        defKeyboard();
                        System.out.println(b1);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "button2" ->{
                        react = "Definition";
                        message.setChatId(chId);
                        InlineKeyboardMarkup ikm = new InlineKeyboardMarkup();
                        List<InlineKeyboardButton> keyboard = new ArrayList<>();
                        InlineKeyboardButton button1 = new InlineKeyboardButton("Алгоритм");
                        InlineKeyboardButton button2 = new InlineKeyboardButton("Структура данных");
                        button1.setCallbackData("alg");
                        button2.setCallbackData("struct");
                        keyboard.add(button1);
                        keyboard.add(button2);
                        ikm.setKeyboard(Collections.singletonList(keyboard));
                        message.setReplyMarkup(ikm);
                        message.setText("Выберете один из пунктов меню");
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "button3"->{
                        react = "Description";
                        message.setChatId(chId);
                        message.setText("Введите название алгоритма");
                        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
                        replyKeyboardRemove.setRemoveKeyboard(true);
                        replyKeyboardRemove.setSelective(false);
                        message.setChatId(String.valueOf(callbackQuery.getMessage().getChatId()));
                        message.setReplyMarkup(replyKeyboardRemove);
                        isAlg = true;
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "button4"->{
                        react = "Example";
                        message.setChatId(chId);
                        InlineKeyboardMarkup ikm = new InlineKeyboardMarkup();
                        List<InlineKeyboardButton> keyboard = new ArrayList<>();
                        InlineKeyboardButton button1 = new InlineKeyboardButton("Алгоритм");
                        InlineKeyboardButton button2 = new InlineKeyboardButton("Структура данных");
                        button1.setCallbackData("alg");
                        button2.setCallbackData("struct");
                        keyboard.add(button1);
                        keyboard.add(button2);
                        ikm.setKeyboard(Collections.singletonList(keyboard));
                        message.setReplyMarkup(ikm);
                        message.setText("Выберете один из пунктов меню");
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "alg"->{
                        message.setChatId(chId);
                        isAlg = true;
                        message.setText("Введите название алгоритма");
                        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
                        replyKeyboardRemove.setRemoveKeyboard(true);
                        replyKeyboardRemove.setSelective(false);
                        message.setChatId(String.valueOf(callbackQuery.getMessage().getChatId()));
                        message.setReplyMarkup(replyKeyboardRemove);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "struct"->{
                        message.setChatId(chId);
                        isAlg = false;
                        message.setText("Введите название структуры данных");
                        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
                        replyKeyboardRemove.setRemoveKeyboard(true);
                        replyKeyboardRemove.setSelective(false);
                        message.setChatId(String.valueOf(callbackQuery.getMessage().getChatId()));
                        message.setReplyMarkup(replyKeyboardRemove);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
    }
    public int getId(String query, String name){
        int id1 = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                do{
                    int id = resultSet.getInt("Id");
                    String name1 = resultSet.getString("AorS");
                    name = name.toLowerCase();
                    name1 = name1.toLowerCase();
                    if(name.equals(name1)){
                        id1 = id;
                    }
                } while (resultSet.next());
            } else{
                message.setText("Не найдено");
                execute(message);
            }
            statement = null;
            resultSet = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return id1;
    }
    public String query(String query){
        String mes = "abc";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                mes = "Полный список: ";
                do{
                    int id = resultSet.getInt("Id");
                    String name = resultSet.getString("AorS");
                    boolean isAlg = resultSet.getBoolean("isAlg");
                    AlgOrStr algOrStr = new AlgOrStr(id,name,isAlg);
                    mes+="\n"+algOrStr.toString();
                } while (resultSet.next());
            } else{
                mes = "Список не найден";
            }
            statement = null;
            resultSet = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mes;
    }
    public String query1(String query, String name){
        String mes = "";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                mes = name+ ": ";
                do{
                    String def = resultSet.getString(react);
                    mes+=def;
                } while (resultSet.next());
            } else{
                mes = "Список не найден q1";
            }
            statement = null;
            resultSet = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mes;
    }
    public void defKeyboard(){
        InlineKeyboardMarkup ikm1 = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboard = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton("Просмотреть полный список");
        InlineKeyboardButton button2 = new InlineKeyboardButton("Получить определение");
        InlineKeyboardButton button3 = new InlineKeyboardButton("Получить описание алгоритма");
        InlineKeyboardButton button4 = new InlineKeyboardButton("Получить пример использования");
        button1.setCallbackData("button1");
        button2.setCallbackData("button2");
        button3.setCallbackData("button3");
        button4.setCallbackData("button4");
        keyboard.add(button1);
        keyboard.add(button2);
        keyboard.add(button3);
        keyboard.add(button4);
        ikm1.setKeyboard(Collections.singletonList(keyboard));
        message.setReplyMarkup(ikm1);
    }
    @Override
    public String getBotUsername() {
        return "My_Structures_Of_Data_bot";
    }
    @Override
    public String getBotToken(){
        return "6175489500:AAGyH_vabv2FkOjn-46XEt9ACTQb11u1vG8";
    }
}
