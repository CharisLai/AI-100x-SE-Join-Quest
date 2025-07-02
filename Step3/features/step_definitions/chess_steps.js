const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const ChessService = require('../../src/ChessService');

let chessService;
let moveResult;
let capturedPiece;

Given('the board is empty except for a Red General at (1, 6)', function () {
    chessService = new ChessService();
    chessService.setPiece('RedGeneral', 1, 6);
});

Given('the board is empty except for a Red General at (2, 5)', function () {
    chessService = new ChessService();
    chessService.setPiece('RedGeneral', 2, 5);
});

Given('the board has:', function (dataTable) {
    chessService = new ChessService();
    const pieces = dataTable.hashes();
    for (const { Piece, Position } of pieces) {
        const match = Position.match(/\((\d+),\s*(\d+)\)/);
        if (match) {
            const row = parseInt(match[1], 10);
            const col = parseInt(match[2], 10);
            chessService.setPiece(Piece.replace(/ /g, ''), row, col);
        }
    }
});

Given('the board is empty except for a Red Guard at (1, 4)', function () {
    throw new Error('pending');
});

When('Red moves the Guard from (1, 4) to (2, 5)', function () {
    throw new Error('pending');
});

Then(/^the move is legal$/, function () {
    assert.strictEqual(moveResult, true);
});

Then(/^the move is illegal$/, function () {
    assert.strictEqual(moveResult, false);
});

Then(/^Red wins immediately$/, function () {
    if (!moveResult) throw new Error('move not legal');
    assert.strictEqual(capturedPiece, 'BlackGeneral');
});

Then(/^the game is not over just from that capture$/, function () {
    if (!moveResult) throw new Error('move not legal');
    assert.notStrictEqual(capturedPiece, 'BlackGeneral');
});

// 泛用 Given 步驟定義
Given(/^the board is empty except for a (.+) at \((\d+), (\d+)\)$/, function (piece, row, col) {
    chessService = new ChessService();
    chessService.setPiece(piece.replace(/ /g, ''), parseInt(row, 10), parseInt(col, 10));
});

// 泛用 When 步驟定義
When(/^Red moves the (.+) from \((\d+), (\d+)\) to \((\d+), (\d+)\)$/, function (piece, fromRow, fromCol, toRow, toCol) {
    const method = {
        'General': 'moveGeneral',
        'Guard': 'moveGuard',
        'Rook': 'moveRook',
        'Horse': 'moveHorse',
        'Cannon': 'moveCannon',
        'Elephant': 'moveElephant',
        'Soldier': 'moveSoldier',
    }[piece];
    if (!method) throw new Error('No move method for piece: ' + piece);
    capturedPiece = chessService.board[parseInt(toRow, 10) - 1][parseInt(toCol, 10) - 1];
    moveResult = chessService[method]([
        parseInt(fromRow, 10),
        parseInt(fromCol, 10)
    ], [
        parseInt(toRow, 10),
        parseInt(toCol, 10)
    ]);
}); 