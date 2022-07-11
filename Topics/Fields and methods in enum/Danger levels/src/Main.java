enum DangerLevel {
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    int x;

    DangerLevel(int x) {
        this.x = x;
    }
    public int getLevel(){
        return this.x;
    }

}