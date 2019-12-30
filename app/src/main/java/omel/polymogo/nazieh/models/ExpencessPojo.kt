package omel.polymogo.nazieh.models

class ExpencessPojo {

    var userId: String? = null
    var date: String? = null
    var expenses: Double? = null
    var statatment: String? = null


    constructor() {

    }

    constructor(
        userId: String?,
        date: String?,
        expenses: Double?,
        statatment: String?

    ) {
        this.userId = userId
        this.date = date
        this.expenses = expenses
        this.statatment = statatment

    }
}