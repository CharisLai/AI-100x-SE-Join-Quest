class ChessService {
    constructor() {
        this.board = Array.from({ length: 10 }, () => Array(9).fill(null));
    }

    setPiece(piece, row, col) {
        this.board[row - 1][col - 1] = piece;
    }

    moveGeneral(from, to) {
        // 僅允許紅將在宮內橫向或直向一步
        const [fromRow, fromCol] = from;
        const [toRow, toCol] = to;
        // 紅將宮範圍：row 1~3, col 4~6
        const inPalace = (r, c) => r >= 1 && r <= 3 && c >= 4 && c <= 6;
        if (!inPalace(toRow, toCol)) return false;
        const dr = Math.abs(toRow - fromRow);
        const dc = Math.abs(toCol - fromCol);
        if (dr + dc !== 1) return false;

        // 兩將照面判斷：同一欄，且中間無其他棋子
        let blackGeneralRow = null;
        for (let r = 0; r < 10; r++) {
            if (this.board[r][toCol - 1] === 'BlackGeneral') {
                blackGeneralRow = r + 1;
                break;
            }
        }
        if (blackGeneralRow) {
            const minR = Math.min(toRow, blackGeneralRow);
            const maxR = Math.max(toRow, blackGeneralRow);
            let blocked = false;
            for (let r = minR; r < maxR - 1; r++) {
                if (this.board[r][toCol - 1]) {
                    blocked = true;
                    break;
                }
            }
            if (!blocked) return false;
        }
        return true;
    }

    moveGuard(from, to) {
        const [fromRow, fromCol] = from;
        const [toRow, toCol] = to;
        // 士/Guard 只能在宮內斜走一步
        const inPalace = (r, c) => r >= 1 && r <= 3 && c >= 4 && c <= 6;
        if (!inPalace(toRow, toCol)) return false;
        const dr = Math.abs(toRow - fromRow);
        const dc = Math.abs(toCol - fromCol);
        if (dr === 1 && dc === 1) return true;
        return false;
    }

    moveRook(from, to) {
        const [fromRow, fromCol] = from;
        const [toRow, toCol] = to;
        // 只能直線移動
        if (fromRow !== toRow && fromCol !== toCol) return false;
        // 檢查路徑是否有阻擋
        if (fromRow === toRow) {
            const minC = Math.min(fromCol, toCol) + 1;
            const maxC = Math.max(fromCol, toCol) - 1;
            for (let c = minC; c <= maxC; c++) {
                if (this.board[fromRow - 1][c - 1]) return false;
            }
        } else {
            const minR = Math.min(fromRow, toRow) + 1;
            const maxR = Math.max(fromRow, toRow) - 1;
            for (let r = minR; r <= maxR; r++) {
                if (this.board[r - 1][fromCol - 1]) return false;
            }
        }
        return true;
    }

    moveHorse(from, to) {
        const [fromRow, fromCol] = from;
        const [toRow, toCol] = to;
        const dr = toRow - fromRow;
        const dc = toCol - fromCol;
        // 馬走日：兩直一橫或兩橫一直
        if (!((Math.abs(dr) === 2 && Math.abs(dc) === 1) || (Math.abs(dr) === 1 && Math.abs(dc) === 2))) return false;
        // 檢查「蹩馬腿」
        if (Math.abs(dr) === 2) {
            const legRow = fromRow + dr / 2;
            if (this.board[legRow - 1][fromCol - 1]) return false;
        } else {
            const legCol = fromCol + dc / 2;
            if (this.board[fromRow - 1][legCol - 1]) return false;
        }
        return true;
    }

    moveCannon(from, to) {
        const [fromRow, fromCol] = from;
        const [toRow, toCol] = to;
        // 只能直線移動
        if (fromRow !== toRow && fromCol !== toCol) return false;
        let screenCount = 0;
        if (fromRow === toRow) {
            const minC = Math.min(fromCol, toCol) + 1;
            const maxC = Math.max(fromCol, toCol) - 1;
            for (let c = minC; c <= maxC; c++) {
                if (this.board[fromRow - 1][c - 1]) screenCount++;
            }
        } else {
            const minR = Math.min(fromRow, toRow) + 1;
            const maxR = Math.max(fromRow, toRow) - 1;
            for (let r = minR; r <= maxR; r++) {
                if (this.board[r - 1][fromCol - 1]) screenCount++;
            }
        }
        // 目標格有棋子，必須剛好隔一子
        const target = this.board[toRow - 1][toCol - 1];
        if (target) {
            return screenCount === 1;
        } else {
            return screenCount === 0;
        }
    }

    moveElephant(from, to) {
        const [fromRow, fromCol] = from;
        const [toRow, toCol] = to;
        // 只能走田字（斜2格）
        if (Math.abs(toRow - fromRow) !== 2 || Math.abs(toCol - fromCol) !== 2) return false;
        // 紅象不能過河（row 1~5）
        if (toRow > 5) return false;
        // 中點不能有阻擋
        const midRow = (fromRow + toRow) / 2;
        const midCol = (fromCol + toCol) / 2;
        if (this.board[midRow - 1][midCol - 1]) return false;
        return true;
    }

    moveSoldier(from, to) {
        const [fromRow, fromCol] = from;
        const [toRow, toCol] = to;
        const dr = toRow - fromRow;
        const dc = toCol - fromCol;
        // 只能走一步
        if (Math.abs(dr) + Math.abs(dc) !== 1) return false;
        // 未過河只能前進
        if (fromRow <= 5) {
            return dr === 1 && dc === 0;
        } else {
            // 過河後不能後退
            return (dr === 1 && dc === 0) || (dr === 0 && Math.abs(dc) === 1);
        }
    }
}

module.exports = ChessService; 