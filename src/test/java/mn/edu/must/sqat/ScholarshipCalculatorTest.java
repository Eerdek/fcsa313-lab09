package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ScholarshipCalculatorTest {

    private static final double EPS = 1e-9;

    private final ScholarshipCalculator calc = new ScholarshipCalculator();

    // ---------- Хэсэг A: happy path ----------

    @Test
    void letterGrade_score95_returnsA() {
        assertEquals("A", calc.letterGrade(95));
    }

    @Test
    void letterGrade_score85_returnsB() {
        assertEquals("B", calc.letterGrade(85));
    }

    @Test
    void gpa_scores95And85_returns3point5() {
        assertEquals(3.5, calc.gpa(new double[] {95, 85}), EPS);
    }

    @Test
    void scholarship_gpa39Credits16NoViolation_returns100() {
        assertEquals(100, calc.scholarship(3.9, 16, false));
    }

    @Test
    void scholarship_gpa35Credits12NoViolation_returns70() {
        assertEquals(70, calc.scholarship(3.5, 12, false));
    }
}
