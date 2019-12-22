package omel.polymogo.nazieh.models

/**
 * Created by MrTayyab on 2/24/2018.
 */
class User {

    // MOdel class
    var name : String? = null
    var phone : String? = null
    var id : String? = null

    constructor(){

    }
    constructor(name: String?, phone: String?,id:String?) {
        this.name = name
        this.phone = phone
        this.id = id
    }
}