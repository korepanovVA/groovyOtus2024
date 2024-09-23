import org.apache.poi.ss.usermodel.*

// Класс для работы с отдельной строкой в xlsx-документе
class RowBuilder {
    Row row
    Workbook workbook

    RowBuilder(Row row, Workbook workbook) {
        this.row = row
        this.workbook = workbook
    }

    void cell(Map<String, Object> options = [:]) {
        int idx = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum()
        Cell cell = row.createCell(idx)
        setValue(cell, options.get('value'))
        if (options.containsKey('style')) {
            CellStyle style = options.get('style')
            cell.setCellStyle(style)
        }
    }

    private void setValue(Cell cell, Object value) {
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value)
        } else if (value instanceof String) {
            cell.setCellValue((String) value)
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value)
        }
    }
}
