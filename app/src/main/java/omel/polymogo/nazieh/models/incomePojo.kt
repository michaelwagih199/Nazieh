package omel.polymogo.nazieh.models

class incomePojo {
    var userId: String? = null
    var date: String? = null
    var totalIncome: Double? = null
    var notes: String? = null
    var customerName: String? = null

    constructor() {

    }

    constructor(
        userId: String?,
        date: String?,
        totalIncome: Double?,
        notes: String?,
        customerName: String?
    ) {
        this.userId = userId
        this.date = date
        this.totalIncome = totalIncome
        this.notes = notes
        this.customerName = customerName
    }
}
