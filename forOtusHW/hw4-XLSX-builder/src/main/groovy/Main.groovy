static void main(String[] args) {
    new XlsxBuilder()
            .filename('example')
            .sheet(name: 'First Sheet') {
                row(idx: 0) {
                    cell(value: 'ID')
                    cell(value: 'Name')
                    cell(value: 'Active')
                }
                row(idx: 1) {
                    cell(value: 1)
                    cell(value: 'Test1 test1')
                    cell(value: true)
                }
                row(idx: 2) {
                    cell(value: 2)
                    cell(value: 'Test2 test2')
                    cell(value: false)
                }
            }
            .sheet(idx: 1) {
                row {
                    cell(value: 'Data in second sheet')
                }
            }
            .build() // Сохраняем файл
}