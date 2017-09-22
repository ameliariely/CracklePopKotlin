
fun main(args : Array<String>) {
    printCracklePop();
}

fun printCracklePop() {
    for (i in 1..100) {
        if (i % 3 == 0 || i % 5 == 0) {
            //print something special
            if (i % 3 == 0) {
                print("Crackle")
            }
            if (i % 5 == 0) {
                print("Pop")
            }
            println()
        } else {
            //just print a boring old number
            println(i)
        }
    }
}
