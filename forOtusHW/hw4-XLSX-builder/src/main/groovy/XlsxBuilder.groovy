// Подключаем зависимость Apache POI для работы с xlsx
@Grab('org.apache.poi:poi-ooxml:5.2.3')
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.ss.usermodel.*

class XlsxBuilder {
    String filename = 'default.xlsx' // Имя файла по умолчанию
    XSSFWorkbook workbook = new XSSFWorkbook()

    // Метод для задания имени файла
    XlsxBuilder filename(String name) {
        // Если расширение не указано, добавляем .xlsx
        this.filename = name.toString().endsWith('.xlsx') ? name : "${name}.xlsx"
        return this
    }

    // Метод для создания страницы (листа)
    XlsxBuilder sheet(Map params = [:], Closure content) {
        // Получаем номер страницы (по умолчанию — следующий индекс)
        int sheetIdx = params.idx ?: workbook.getNumberOfSheets()
        // Если имя не указано, используем индекс
        String sheetName = params.name ?: "Sheet$sheetIdx"
        Sheet sheet = workbook.createSheet(sheetName)

        // Делегируем управление SheetBuilder
        content.delegate = new SheetBuilder(sheet)
        content.resolveStrategy = Closure.DELEGATE_FIRST
        content()

        return this
    }

    // Метод для сохранения файла
    void build() {
        FileOutputStream fileOut = new FileOutputStream(filename)
        workbook.write(fileOut)
        fileOut.close()
        workbook.close()
        println "Файл сохранен: $filename"
    }
}