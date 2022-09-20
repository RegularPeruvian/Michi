package pe.galiano.michi

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val winnerPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )
    val symbolsSourceList = arrayOf("A", "B", "Y", "Z", "K", "L", "W", "J", "Q", "F")
    val buttonColors = arrayOf("#c0392b", "#8e44ad", "#16a085", "#f1c40f", "#f1c40f ")

    var michiOne: Button? = null
    var michiTwo: Button? = null
    var michiThree: Button? = null
    var michiFour: Button? = null
    var michiFive: Button? = null
    var michiSix: Button? = null
    var michiSeven: Button? = null
    var michiEight: Button? = null
    var michiNine: Button? = null
    var resultTextView: TextView? = null

    var turnsCounter = 0
    var isGameActive = true
    var activePlayer = 0
    var cardsState = mutableListOf(2, 2, 2, 2, 2, 2, 2, 2, 2)

    var firstPlayerSymbol = "O"
    var secondPlayerSymbol = "X"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        michiOne = findViewById(R.id.michiOne)
        michiTwo = findViewById(R.id.michiTwo)
        michiThree = findViewById(R.id.michiThree)
        michiFour = findViewById(R.id.michiFour)
        michiFive = findViewById(R.id.michiFive)
        michiSix = findViewById(R.id.michiSix)
        michiSeven = findViewById(R.id.michiSeven)
        michiEight = findViewById(R.id.michiEight)
        michiNine = findViewById(R.id.michiNine)
        resultTextView = findViewById(R.id.michiResult)

        michiOne?.setOnClickListener { touch(it as Button) }
        michiTwo?.setOnClickListener { touch(it as Button) }
        michiThree?.setOnClickListener { touch(it as Button) }
        michiFour?.setOnClickListener { touch(it as Button) }
        michiFive?.setOnClickListener { touch(it as Button) }
        michiSix?.setOnClickListener { touch(it as Button) }
        michiSeven?.setOnClickListener { touch(it as Button) }
        michiEight?.setOnClickListener { touch(it as Button) }
        michiNine?.setOnClickListener { touch(it as Button) }
    }

    fun touch(button: Button) {
        val touchedCard = Integer.parseInt(button.tag as String)

        if (isGameActive.not()) {
            resetGame()
        }

        if (cardsState[touchedCard] == 2) {
            turnsCounter++
        }

        if (turnsCounter == 9) {
            isGameActive = false
        }

        cardsState[touchedCard] = activePlayer

        if (activePlayer == 0) {
            button.text = firstPlayerSymbol
            activePlayer = 1
            updateResult("Le toca al jugador $secondPlayerSymbol")
        } else {
            button.text = secondPlayerSymbol
            activePlayer = 0
            updateResult("Le toca al jugador $firstPlayerSymbol")
        }

        var isGameWon = false

        for (winnerPosition in winnerPositions) {
            if (cardsState[winnerPosition[0]] == cardsState[winnerPosition[1]] &&
                cardsState[winnerPosition[1]] == cardsState[winnerPosition[2]] &&
                cardsState[winnerPosition[0]] != 2
            ) {
                isGameWon = true
                isGameActive = false

                val winnerText = if (activePlayer == 0) {
                    "El jugador $secondPlayerSymbol ganó"
                } else {
                    "El jugador $firstPlayerSymbol ganó"
                }
                updateResult(winnerText)
            }
        }

        if (turnsCounter == 9 && isGameWon.not()) {
            updateResult("Han empatado zzz")
        }
    }

    fun resetGame() {
        isGameActive = true
        activePlayer = 0
        turnsCounter = 0

        for (index in 0 until cardsState.size) {
            cardsState[index] = 2
        }

        val randomColor = Color.parseColor(buttonColors[(buttonColors.indices).random()])

        michiOne?.text = ""
        michiOne?.setBackgroundColor(randomColor)
        michiTwo?.text = ""
        michiTwo?.setBackgroundColor(randomColor)
        michiThree?.text = ""
        michiThree?.setBackgroundColor(randomColor)
        michiFour?.text = ""
        michiFour?.setBackgroundColor(randomColor)
        michiFive?.text = ""
        michiFive?.setBackgroundColor(randomColor)
        michiSix?.text = ""
        michiSix?.setBackgroundColor(randomColor)
        michiSeven?.text = ""
        michiSeven?.setBackgroundColor(randomColor)
        michiEight?.text = ""
        michiEight?.setBackgroundColor(randomColor)
        michiNine?.text = ""
        michiNine?.setBackgroundColor(randomColor)

        val randomSymbols = mutableListOf<String>()
        randomSymbols.addAll(symbolsSourceList)

        val firstRandom = (randomSymbols.indices).random()
        firstPlayerSymbol = randomSymbols[firstRandom]

        randomSymbols.removeAt(firstRandom)

        val secondRandom = (randomSymbols.indices).random()
        secondPlayerSymbol = randomSymbols[secondRandom]

        updateResult("Le toca al jugador $firstPlayerSymbol")
    }

    fun updateResult(result: String) {
        resultTextView?.text = result
    }

}