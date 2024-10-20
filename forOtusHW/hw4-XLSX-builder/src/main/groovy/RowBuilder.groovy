@Grab('org.apache.poi:poi-ooxml:5.2.2')
import org.apache.poi.ss.usermodel.*

class RowBuilder {
    Row row

    // Конструктор для RowBuilder
    RowBuilder(Row row) {
        this.row = row
    }

    // Метод для создания ячейки
    void cell(Map params) {
        // Индекс для ячейки (следующая по порядку)
        int cellIdx = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum()
        Cell cell = row.createCell(cellIdx)

        // Устанавливаем значение для ячейки (поддерживаются int, string, boolean)
        if (params.value instanceof Integer) {
            cell.setCellValue((int) params.value)
        } else if (params.value instanceof String) {
            cell.setCellValue((String) params.value)
        } else if (params.value instanceof Boolean) {
            cell.setCellValue((boolean) params.value)
        }

        // Если задан стиль, применяем его
        if (params.style) {
            CellStyle style = row.sheet.workbook.createCellStyle()
            // Пример настройки выравнивания (можно добавить больше стилей)
            style.alignment = params.style
            cell.setCellStyle(style)
        }
    }
}