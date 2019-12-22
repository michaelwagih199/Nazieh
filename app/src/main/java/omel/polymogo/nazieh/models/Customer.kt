package omel.polymogo.nazieh.models

class Customer(val id: String?, val totalValue:Double , val content:String ,
               val contractorName:String ,
               val Date:String ,
               val ReceivingAmount:Double , val RemaininAmount : Double ,
               val  recipientName :String){

 constructor():this("", 0.0,
     "","",
     "",0.0,
     0.0,
     "")
}

