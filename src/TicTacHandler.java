interface TicTacHandler{
    void ReceiveMove (TicTacMove move);

    void SendMove(int row, int col);

    void Reset();


}
