import java.util.Enumeration;
import java.util.Vector;

/**
 * @author viktor email kuchin.victor@gmail.com
 */
class Customer {
    public static final int REGULAR_DAYS = 2;
    public static final int NEW_RELEASE_DAYS = 3;
    private String _name;
    private Vector _rentals = new Vector();

    public Customer(String name) {
        _name = name;
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Enumeration rentals = _rentals.elements();
        String result = "Учет аренды для " + getName() + "\n";
        while (rentals.hasMoreElements()) {
            double thisAmount = 0;
            Rental each = (Rental) rentals.nextElement();
            //определить сумму для каждой строки
            switch (each.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                    thisAmount += REGULAR_DAYS;
                    if (each.getDaysRented() > REGULAR_DAYS) {
                        thisAmount += (each.getDaysRented() - REGULAR_DAYS) * 1.5;
                    }
                    break;
                case Movie.NEW_RELEASE:
                    thisAmount += each.getDaysRented() * NEW_RELEASE_DAYS;
                    break;
                case Movie.CHILDRENS:
                    thisAmount += 1.5;
                    if (each.getDaysRented() > NEW_RELEASE_DAYS) {
                        thisAmount += (each.getDaysRented() - NEW_RELEASE_DAYS) * 1.5;
                    }
                    break;
            }
            // добавить очки для активного арендатора
            frequentRenterPoints += 1;
            // бонус за аренду новинки на два дня
            if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE)
                && each.getDaysRented() > 1) {
                frequentRenterPoints += 1;
            }
            //показать результаты для этой аренды
            result += "\t" + each.getMovie().getTitle() + "\t" +
                String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }
        //добавить нижний колонтитул
        result += "Сумма задолженности составляет " +
            String.valueOf(totalAmount) + "\n";
        result += "Вы заработали " + String.valueOf(frequentRenterPoints) +
            " очков за активность";
        return result;
    }

    public void addRental(Rental arg) {
        _rentals.addElement(arg);
    }

    public String getName() {
        return _name;
    }

}