public class LearnExtend implements Comparable<LearnExtend> {
    private int m_nID;
    private double m_dBonus;


    public LearnExtend(int id, double bonus) {
        m_nID = id;
        m_dBonus = bonus;
    }

    public void setID(int id) {
        m_nID = id;
    }

    public void setBonus(double bonus) {
        m_dBonus = bonus;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int compareTo(LearnExtend o) {
        return m_nID;
    }
}
