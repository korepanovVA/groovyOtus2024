import org.apache.poi.ss.usermodel.*

// Класс для работы с отдельным листом (страницей) в xlsx-документе
class SheetBuilder {
    Sheet sheet
    Workbook workbook

    SheetBuilder(Sheet sheet, Workbook workbook) {
        this.sheet = sheet
        this.workbook = workbook
    }

    void row(Map<String, Object> options = [:], Closure<?> closure) {
        int idx = options.getOrDefault('idx', sheet.getLastRowNum() + 1)
        Row row = sheet.createRow(idx)
        closure.delegate = new RowBuilder(row, workbook)
        closure()
    }
}