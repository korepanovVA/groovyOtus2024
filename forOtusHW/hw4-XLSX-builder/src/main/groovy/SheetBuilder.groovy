@Grab('org.apache.poi:poi-ooxml:5.2.3')
import org.apache.poi.ss.usermodel.*

class SheetBuilder {
    Sheet sheet

    // Конструктор для SheetBuilder
    SheetBuilder(Sheet sheet) {
        this.sheet = sheet
    }

    // Метод для создания строки с параметрами
    void row(Map params, Closure content) {
        // Номер строки (по умолчанию следующая строка)
        int rowIdx = params.idx ?: sheet.getLastRowNum() + 1
        Row row = sheet.createRow(rowIdx)

        // Делегируем управление RowBuilder
        content.delegate = new RowBuilder(row)
        content.resolveStrategy = Closure.DELEGATE_FIRST
        content()
    }

    // Метод для создания строки без параметров
    void row(Closure content) {
        // Номер строки (по умолчанию следующая строка)
        int rowIdx = sheet.getLastRowNum() + 1
        Row row = sheet.createRow(rowIdx)

        // Делегируем управление RowBuilder
        content.delegate = new RowBuilder(row)
        content.resolveStrategy = Closure.DELEGATE_FIRST
        content()
    }
}