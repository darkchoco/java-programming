import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class App
{
    public static void main(String[] args) {
        File dir = new File(args[0]);
        String[] filenames = dir.list();
        assert filenames != null;
        for (String s : filenames) {
            System.out.println("file: " + s);
        }

        try {
            Files.list(Path.of(args[0]))
                    .filter(file -> !Files.isDirectory(file))  // file만.
                    .sorted()
//                    .map(String::valueOf)
                    .forEach(item -> {
                        String filename = item.getFileName().toString();
                        String new_fileName = changeDateTime(
                                filename.substring(0, filename.lastIndexOf('.')),
                                -1200);
                        try {
                            Files.move(
                                    item,
                                    item.resolveSibling(new_fileName + '.' + getFileExt(filename)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String changeDateTime(String originalTime, int delta_seconds) {
        // https://stackoverflow.com/questions/9015536/how-to-add-10-minutes-to-my-string-time
        String newTime = null;

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Date d = df.parse(originalTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.SECOND, delta_seconds);
            newTime = df.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newTime;
    }

    private static String getFileExt(String fn) {
        if (fn.lastIndexOf('.') != -1 && fn.lastIndexOf('.') != 0)
            return fn.substring(fn.lastIndexOf('.')+1);
        else
            return "";
    }
}
