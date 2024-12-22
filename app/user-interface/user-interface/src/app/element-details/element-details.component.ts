import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface Expense {
  id: string;
  name: string;
  description: string;
  amount: number;
  category: { id: string, name: string };
}

@Component({
  selector: 'app-element-details',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './element-details.component.html',
  styleUrls: ['./element-details.component.css'],
})
export class ElementDetailsComponent implements OnInit {
  expense: Expense | null = null;

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('elementId');
    if (id) {
      this.fetchExpenseDetails(id);
    } else {
      throw new Error('Expense ID not provided');
    }
  }

  fetchExpenseDetails(expenseId: string): void {
    this.http.get<Expense>(`http://localhost:8083/expenses/${expenseId}`).subscribe(
      (data) => {
        this.expense = data;
      },
      (error) => {
        console.error('Error fetching expense details', error);
      }
    );
  }
}
