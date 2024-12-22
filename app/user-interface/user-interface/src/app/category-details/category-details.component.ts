import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface Category {
  id: string;
  name: string;
  description: string;
  budget: number;
}
interface Expense{
  id: string;
  name: string;
}
interface ExpenseList{
  expenses: Expense[];
}

@Component({
  selector: 'app-category-details',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './category-details.component.html',
  styleUrls: ['./category-details.component.css'],
})
export class CategoryDetailsComponent implements OnInit {
  category: Category | null = null;
  expenses: Expense[] = [];

  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.fetchCategoryDetails(id);
      this.fetchExpensesByCategory(id);
    } else {
      throw new Error('Category ID not provided');
    }
  }

  fetchCategoryDetails(categoryId: string): void {
    this.http.get<Category>(`http://localhost:8083/categories/${categoryId}`).subscribe(
      (data) => {
        this.category = data;
      },
      (error) => {
        console.error('Error fetching category details', error);
      }
    );
  }
  fetchExpensesByCategory(categoryId: string): void{
    this.http.get<ExpenseList>(`http://localhost:8083/expenses/category/${categoryId}`).subscribe(
      (data) => {
        this.expenses = data.expenses;
      },
      (error) => {
        console.error('Error fetching category expenses', error);
      }
    )
  }
  viewExpense(expenseId: string): void{
    this.router.navigate(['categories/'+this.category?.id+'/elements/'+expenseId]);
  }
  editExpense(expenseId: string):void{
    this.router.navigate(['categories/'+this.category?.id+'/edit-element/'+expenseId]);
  }
}