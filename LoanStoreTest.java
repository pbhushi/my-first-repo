import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LoanStoreTest {
    private LoanStore loanStore;
    private SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp() {
        loanStore = new LoanStore();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Test
    void testAddLoan() throws ParseException {
        Date paymentDate = dateFormat.parse("05/06/2023");
        Date dueDate = dateFormat.parse("05/07/2023");
        Loan loan = new Loan("L1", "C1", "LEN1", 10000, paymentDate, 0.01, dueDate, 0.01);
        loanStore.addLoan(loan);

        List<Loan> loans = loanStore.getLoans();
        assertEquals(1, loans.size());
        assertEquals(loan, loans.get(0));
    }

    @Test
    void testGetLoansByLender() throws ParseException {
        Date paymentDate1 = dateFormat.parse("05/06/2023");
        Date dueDate1 = dateFormat.parse("05/07/2023");
        Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, paymentDate1, 0.01, dueDate1, 0.01);

        Date paymentDate2 = dateFormat.parse("01/06/2023");
        Date dueDate2 = dateFormat.parse("05/08/2023");
        Loan loan2 = new Loan("L2", "C1", "LEN1", 20000, paymentDate2, 0.01, dueDate2, 0.01);

        loanStore.addLoan(loan1);
        loanStore.addLoan(loan2);

        List<Loan> loansByLender = loanStore.getLoansByLender("LEN1");
        assertEquals(2, loansByLender.size());
        assertTrue(loansByLender.contains(loan1));
        assertTrue(loansByLender.contains(loan2));
    }

    @Test
    void testGroupLoansByInterest() throws ParseException {
        Date paymentDate1 = dateFormat.parse("04/04/2023");
        Date dueDate1 = dateFormat.parse("04/05/2023");
        Loan loan1 = new Loan("L3", "C2", "LEN2", 50000, paymentDate1, 0.02, dueDate1, 0.02);

        Date paymentDate2 = dateFormat.parse("04/04/2023");
        Date dueDate2 = dateFormat.parse("04/05/2023");
        Loan loan2 = new Loan("L4", "C3", "LEN2", 50000, paymentDate2, 0.02, dueDate2, 0.02);

        loanStore.addLoan(loan1);
        loanStore.addLoan(loan2);

        Map<Double, List<Loan>> loansByInterest = loanStore.groupLoansByInterest();
        assertEquals(1, loansByInterest.size());
        assertTrue(loansByInterest.containsKey(0.02));
        assertEquals(2, loansByInterest.get(0.02).size());
    }

    @Test
    void testGroupLoansByCustomerId() throws ParseException {
        Date paymentDate1 = dateFormat.parse("05/06/2023");
        Date dueDate1 = dateFormat.parse("05/07/2023");
        Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, paymentDate1, 0.01,
