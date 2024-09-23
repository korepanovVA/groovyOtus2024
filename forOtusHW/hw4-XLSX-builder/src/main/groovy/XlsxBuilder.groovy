import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.SheetBuilder
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.nio.file.Files
import java.nio.file.Paths

// Основной класс для работы с xlsx-документом
class XlsxBuilder {
    String filename
    XSSFWorkbook workbook = new XSSFWorkbook()

    // Конструктор, который задает имя файла (если нет расширения, добавляем .xlsx)
    XlsxBuilder(String filename) {
        this.filename = filename.endsWith('.xlsx') ? filename : "$filename.xlsx"
    }

    // Метод для создания новой страницы (листа) в xlsx-документе
    void sheet(Map<String, Object> options = [:], Closure<?> closure) {
        int idx = options.getOrDefault('idx', workbook.getNumberOfSheets())
        String name = options.getOrDefault('name', "Sheet$idx")
        Sheet sheet = workbook.createSheet(name)
        closure.delegate = new SheetBuilder(sheet, workbook)
        closure()
    }

    // Метод для сохранения созданного xlsx-документа
    void build() {
        def output = Files.newOutputStream(Paths.get(filename))
        workbook.write(output)
        output.close()
        workbook.close()
    }
}
