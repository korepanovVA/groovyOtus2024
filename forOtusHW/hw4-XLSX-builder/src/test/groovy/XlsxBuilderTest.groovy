@Grab('org.apache.poi:poi-ooxml:5.2.2') // Подключаем зависимость Apache POI для работы с xlsx
@Grab('org.spockframework:spock-core:2.0-groovy-3.0') // Зависимость для Spock

import spock.lang.Specification
import org.apache.poi.xssf.usermodel.XSSFWorkbook


class XlsxBuilderTest extends Specification {

    //Тест сохранения файла с правильным именем: проверяет, что файл сохраняется с расширением .xlsx
    // даже если это расширение не указано при передаче имени файла.
    def "XlsxBuilder сохраняет файл с правильным именем"() {
        given: "XlsxBuilder с именем файла example"
        def builder = new XlsxBuilder().filename('example')

        when: "Документ сохраняется"
        builder.build()

        then: "Файл сохраняется с расширением .xlsx"
        def savedFile = new File('example.xlsx')
        savedFile.exists()
        cleanup:
        savedFile.delete() // Удаляем файл после теста
    }

    //Тест создания листа с правильным именем: проверяет, что созданный лист имеет указанное имя.
    def "XlsxBuilder создаёт лист с правильным именем"() {
        given: "XlsxBuilder с листом с именем First Sheet"
        def builder = new XlsxBuilder()
        builder.sheet(name: 'First Sheet') {
            row {
                cell(value: 'Test Data')
            }
        }

        when: "Документ сохраняется"
        builder.build()

        then: "Первый лист называется First Sheet"
        def savedFile = new File('default.xlsx')
        def workbook = new XSSFWorkbook(savedFile)
        def sheet = workbook.getSheetAt(0)
        sheet.getSheetName() == 'First Sheet'
        cleanup:
        workbook.close()
        savedFile.delete() // Удаляем файл после теста
    }

    //Тест создания строки и ячеек с корректными значениями: проверяет, что созданная строка и
    // ячейки содержат правильные значения для различных типов данных (int, string, boolean).
    def "XlsxBuilder создаёт строку и ячейки с корректными значениями"() {
        given: "XlsxBuilder с данными для строки"
        def builder = new XlsxBuilder()
        builder.sheet(name: 'Test Sheet') {
            row {
                cell(value: 1)
                cell(value: 'John Doe')
                cell(value: true)
            }
        }

        when: "Документ сохраняется"
        builder.build()

        then: "Данные в ячейках корректны"
        def savedFile = new File('default.xlsx')
        def workbook = new XSSFWorkbook(savedFile)
        def sheet = workbook.getSheetAt(0)
        def row = sheet.getRow(0)

        row.getCell(0).numericCellValue == 1
        row.getCell(1).stringCellValue == 'John Doe'
        row.getCell(2).booleanCellValue == true

        cleanup:
        workbook.close()
        savedFile.delete() // Удаляем файл после теста
    }

    //Тест создания нескольких листов: проверяет, что создаются несколько листов с правильными именами.
    def "XlsxBuilder создаёт несколько листов"() {
        given: "XlsxBuilder с несколькими листами"
        def builder = new XlsxBuilder()
        builder.sheet(name: 'Sheet1') {
            row {
                cell(value: 'Data1')
            }
        }
        builder.sheet(name: 'Sheet2') {
            row {
                cell(value: 'Data2')
            }
        }

        when: "Документ сохраняется"
        builder.build()

        then: "Созданы два листа с правильными именами"
        def savedFile = new File('default.xlsx')
        def workbook = new XSSFWorkbook(savedFile)
        def sheet1 = workbook.getSheetAt(0)
        def sheet2 = workbook.getSheetAt(1)

        sheet1.getSheetName() == 'Sheet1'
        sheet2.getSheetName() == 'Sheet2'

        cleanup:
        workbook.close()
        savedFile.delete() // Удаляем файл после теста
    }
}