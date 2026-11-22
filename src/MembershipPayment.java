public class MembershipPayment {
    private int memberNumber;
    private int year;
    private int amount;
    private boolean paid;

    public MembershipPayment(int memberNumber, int year, int amount, boolean paid) {
        this.memberNumber = memberNumber;
        this.year = year;
        this.amount = amount;
        this.paid = paid;
    }

    public int getMemberNumber() {
        return memberNumber;
    }

    public int getYear() {
        return year;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}