package com.example.xogamemission

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Grid
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xogamemission.logic.EMPTY_CELL
import com.example.xogamemission.logic.O_PLAYER
import com.example.xogamemission.logic.Result
import com.example.xogamemission.logic.XOGame
import com.example.xogamemission.logic.X_PLAYER

class MainActivity : AppCompatActivity() {

    private lateinit var game: XOGame
    private lateinit var buttons: Array<Array<Button>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        game = XOGame()
        buttons = Array(3) { row ->
            Array(3) { column ->
                Button(this).apply {
                    textSize = 26f
                    setOnClickListener { handleMove(row, column) }
                }
            }
        }

        val gridLayout = findViewById<GridLayout>(R.id.activity_main_grid_layout)
        gridLayout.rowCount = 3
        gridLayout.columnCount = 3

        for (row in 0..2) {
            for (column in 0..2) {
                val button = buttons[row][column]

                val params = GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    rowSpec = GridLayout.spec(row, 1f)
                    columnSpec = GridLayout.spec(column, 1f)
                }

                button.layoutParams = params
                gridLayout.addView(button)
            }
        }

        findViewById<Button>(R.id.activity_main_reset_button).setOnClickListener {
            game.reset()
            updateBoard()
        }

        updateBoard()

    }

    private fun handleMove(row: Int, col: Int) {
        if(game.validateTurn(row, col)) {
            game.move(row, col)
            updateBoard()
            val winner = game.checkForWinner()
            if (winner != null) {
                val message = when (winner) {
                    Result.X_PLAYER -> "Player X Wins!"
                    Result.O_PLAYER -> "Player O Wins!"
                    Result.DRAW_INDICATOR -> "It's a draw!"
                }
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                game.reset()
            }
        }
    }

    private fun updateBoard() {
        val board = game.board
        for (row in board.indices) {
            for (column in board[row].indices) {
                buttons[row][column].text = board[row][column].toString()
                buttons[row][column].isEnabled = board[row][column] == EMPTY_CELL
            }
        }
    }


}