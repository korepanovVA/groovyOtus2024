import org.apache.poi.ss.usermodel.*

// Функция для создания стиля жирного шрифта


static void main(String[] args) {

// Пример использования DSL для создания xlsx-файла с применением стилей
  def xlsxBuilder = new XlsxBuilder("example_with_styles")

// Создаем стиль для заголовков
  def boldStyle = createBoldStyle(xlsxBuilder.workbook)

  xlsxBuilder.sheet(name: "Styled Sheet") {
    // Создаем первую строку с заголовками и применяем стиль
    row {
      cell(value: "Name", style: boldStyle)
      cell(value: "Age", style: boldStyle)
      cell(value: "Active", style: boldStyle)
    }
    // Добавляем данные без стиля
    row {
      cell(value: "Petya")
      cell(value: 15)
      cell(value: true)
    }
    row {
      cell(value: "Vasya")
      cell(value: 26)
      cell(value: false)
    }
  }

// Сохраняем файл
  xlsxBuilder.build()
}