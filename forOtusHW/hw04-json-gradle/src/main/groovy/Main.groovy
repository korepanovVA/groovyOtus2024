static void main(String[] args) {
    EmployeeProcessor processor = new EmployeeProcessor()

    // Получаем путь к локальному файлу из ресурсов
    String localFilePath = Main.getClassLoader().getResource("local_employees.json").getFile()

    // Скачиваем JSON или используем локальный
    String jsonContent = processor.downloadJsonOrFallback("https://example.com/employees.json", localFilePath)

    // Генерация HTML
    String htmlContent = processor.parseJsonToHtml(jsonContent)
    processor.saveToFile(htmlContent, "output/employees.html")

    // Преобразование в XML
    String xmlContent = processor.parseJsonToXml(jsonContent)
    processor.saveToFile(xmlContent, "output/employees.xml")
}