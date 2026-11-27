package Membership;

import User_And_Member.Member;
import User_And_Member.MemberType;
import User_And_Member.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class MembershipFeeService {

    private final List<MembershipPayment> payments = new ArrayList<>();

    public int calculateMembershipFee(Member member) {
        int age = Period.between(member.getBirthday(), LocalDate.now()).getYears();

        if (member.getMemberType() == MemberType.PASSIVE) {
            return 500;
        }

        if (age < 18) {
            return 1000;
        } else if (age > 60) {
            return 1200;
        } else {
            return 1600;
        }
    }

    public int calculateTotalExpectedIncome(List<User> users) {
        int total = 0;

        for (User user : users) {
            if (user instanceof Member member) {
                total += calculateMembershipFee(member);
            }
        }
            return total;
    }

    public int calculateTotalPaidIncome(int year) {
        int total = 0;

        for (MembershipPayment payment : payments) {
            if (payment.getYear() == year && payment.isPaid()) {
                total += payment.getAmount();
            }
        }

        return total;
    }

    public void createMembershipPayment(Member member, int year) {
        for (MembershipPayment payment : payments) {
            if (payment.getMemberNumber() == member.getMemberNumber()
                    && payment.getYear() == year) {
                return;
            }
        }

        int amount = calculateMembershipFee(member);

        MembershipPayment payment =
                new MembershipPayment(member.getMemberNumber(), year, amount, false);

        payments.add(payment);
    }

    public boolean registerPayment(int memberNumber, int year) {
        for (MembershipPayment payment : payments) {
            if (payment.getMemberNumber() == memberNumber && payment.getYear() == year) {

                if (payment.isPaid()){
                    return false;
                }
                payment.setPaid(true);
                return true;
            }
        }

        return false;
    }

    public List<MembershipPayment> getUnpaidPayments(int year) {
        List<MembershipPayment> unpaidPayments = new ArrayList<>();

        for (MembershipPayment payment : payments) {
            if (payment.getYear() == year && !payment.isPaid()) {
                unpaidPayments.add(payment);
            }
        }

        return unpaidPayments;
    }

    public List<MembershipPayment> getPaidPayments(int year){
        List<MembershipPayment> paidPayments = new ArrayList<>();

        for (MembershipPayment payment : payments){
            if (payment.getYear() == year && payment.isPaid()){
                paidPayments.add(payment);
            }
        }
        return paidPayments;
    }

    public int calculateTotalUnpaidIncome(int year) {
        int total = 0;

        for (MembershipPayment payment : payments) {
            if (payment.getYear() == year && !payment.isPaid()) {
                total += payment.getAmount();
            }
        }

        return total;
    }

    public int createPaymentsForAllMembers(List<User> users, int year) {
        int createdPayments = 0;

        for (User user : users) {
            if (user instanceof Member member) {
                int previousSize = payments.size();

                createMembershipPayment(member, year);

                if (payments.size() > previousSize) {
                    createdPayments++;
                }
            }
        }

        return createdPayments;
    }

    public MembershipPayment getMembershipPayment(int memberNumber, int year){
        for (MembershipPayment payment : payments){
            if (payment.getMemberNumber() == memberNumber && payment.getYear() == year){
                return payment;
            }
        }
        return null;
    }

    public boolean payMembershipFee(Member member, int year){
        createMembershipPayment(member,year);
        return registerPayment(member.getMemberNumber(),year);
    }

    public void loadMembershipPayments(List<MembershipPayment> loadedPayments) {
        payments.clear();
        payments.addAll(loadedPayments);
    }

    public List<MembershipPayment> getMembershipPayments() {
        return new ArrayList<>(payments);
    }
}