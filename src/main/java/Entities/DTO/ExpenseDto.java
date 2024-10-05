package com.janek.app;
import lombok.Builder;
import lombok.Getter;
import java.util.Comparator;


@Builder
public class ExpenseDto {
    @Getter
    private String name;
    @Getter
    private String description;
    @Getter
    private double amount;
    @Getter
    private String categoryName;

    private static Comparator<ExpenseDto> byHashComparator = new Comparator<ExpenseDto>() {
        @Override
        public int compare(ExpenseDto o1, ExpenseDto o2) {
            return Integer.compare(o1.hashCode(), o2.hashCode());
        }
    };
    private static Comparator<ExpenseDto> byNaturalOrderComparator = new Comparator<ExpenseDto>() {
        @Override
        public int compare(ExpenseDto o1, ExpenseDto o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

    public int compareByHash(ExpenseDto o1, ExpenseDto o2) {
        return this.byHashComparator.compare(o1, o2);
    }

    public int compareByNaturalOrder(ExpenseDto o1, ExpenseDto o2) {
        return this.byNaturalOrderComparator.compare(o1, o2);
    }

    @Override
    public String toString() {
        return "ExpenseDto{" +
                "name=" + name +
                ", description=" + description +
                ", amount=" + amount +
                ", categoryName=" + categoryName +
                "}";
    }
}
