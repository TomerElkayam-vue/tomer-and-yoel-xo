package com.example.xogamemission.logic

const val X_PLAYER = 'X'
const val O_PLAYER = '0'
const val EMPTY_CELL = ' '

enum class Result {
    X_PLAYER, O_PLAYER, DRAW_INDICATOR
}

class XOGame {
    val board =  Array(3) { CharArray(3) { EMPTY_CELL} }
    private var player : Char = X_PLAYER

    fun reset() {
        for (row in board.indices) {
            for (column in board[row].indices) {
                board[row][column] = EMPTY_CELL
            }
        }

        player = X_PLAYER
    }

    fun validateTurn(row: Int, column: Int) : Boolean {
        return !(row !in board.indices || column !in board.indices|| board[row][column] != EMPTY_CELL)
    }

    fun move(row: Int, column: Int) {
        board[row][column] = player
        player = if (player == X_PLAYER) O_PLAYER else X_PLAYER
    }

    private fun returnResult(winner: Char) : Result {
        return if(winner == X_PLAYER) {
            Result.X_PLAYER
        } else {
            Result.O_PLAYER
        }
    }

    fun checkForWinner(): Result? {
        for (i in board.indices) {
            if (board[i][0] != EMPTY_CELL && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return returnResult(board[i][0])
            }
            if (board[0][i] != EMPTY_CELL && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                return returnResult(board[0][i])
            }
        }

        if (board[0][0] != EMPTY_CELL && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return returnResult(board[0][0])
        }
        if (board[0][2] != EMPTY_CELL && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return returnResult(board[0][2])
        }

        if (board.all { row -> row.all { cell -> cell != EMPTY_CELL } }) {
           return Result.DRAW_INDICATOR
        }

        return null
    }
}