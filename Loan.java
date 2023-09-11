import java.util.*;
import java.util.stream.Collectors;

class Loan {
    private String loanId;
    private String customerId;
    private String lenderId;
    private double amount;
    private double remainingAmount;
    private Date paymentDate;
    private double interestPerDay;
    private Date dueDate;
    private double penaltyPerDay;
    private boolean cancelled;

    public Loan(String loanId, String customerId, String lenderId, double amount,
                Date paymentDate, double interestPerDay, Date dueDate, double penaltyPerDay) {
        this.loanId = loanId;
        this.customerId = customerId;
        this.lenderId = lenderId;
        this.amount = amount;
        this.remainingAmount = amount;
        this.paymentDate = paymentDate;
        this.interestPerDay = interestPerDay;
        this.dueDate = dueDate;
        this.penaltyPerDay = penaltyPerDay;
        this.cancelled = false;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getLenderId() {
        return lenderId;
    }

    public double getAmount() {
        return amount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public double getInterestPerDay() {
        return interestPerDay;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public double getPenaltyPerDay() {
        return penaltyPerDay;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void makePayment(double amountPaid, Date paymentDate) {
        if (paymentDate.after(dueDate)) {
            // Log an alert message
            System.out.println("Loan " + loanId + " has crossed the due date.");
        }

        if (paymentDate.after(dueDate) || paymentDate.after(this.paymentDate)) {
            throw new IllegalArgumentException("Payment date cannot be greater than Due Date or Payment Date.");
        }

        if (amountPaid <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than 0.");
        }

        if (amountPaid > remainingAmount) {
            throw new IllegalArgumentException("Payment amount cannot exceed the remaining amount.");
        }

        remainingAmount -= amountPaid;

        if (remainingAmount == 0) {
            cancelled = true;
        }
    }
}

public class LoanStore {
    private List<Loan> loans = new ArrayList<>();

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public List<Loan> getLoansByLender(String lenderId) {
        return loans.stream()
                .filter(loan -> loan.getLenderId().equals(lenderId))
                .collect(Collectors.toList());
    }

    public Map<Double, List<Loan>> groupLoansByInterest() {
        return loans.stream()
                .collect(Collectors.groupingBy(Loan::getInterestPerDay));
    }

    public Map<String, List<Loan>> groupLoansByCustomerId() {
        return loans.stream()
                .collect(Collectors.groupingBy(Loan::getCustomerId));
    }
}
