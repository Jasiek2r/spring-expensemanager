import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';  // Import CommonModule

interface Category {
  id: string;
  name: string;
}

interface CategoriesResponse {
  expenseCategories: Category[];
}

@Component({
  selector: 'app-categories',
  imports: [CommonModule],
  templateUrl: './categories.component.html',
  styleUrl: './categories.component.css'
})
export class CategoriesComponent {
  categories: Category[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchCategories();
  }

  fetchCategories(): void {
    this.http.get<CategoriesResponse>('http://localhost:8083/categories/').subscribe(
      (data) => {
        console.log('Fetched categories:', data);  // Log the fetched data
        this.categories = data.expenseCategories;
        console.log('Categories after update:', this.categories);
      },
      (error) => {
        console.error('Error fetching categories', error);
      }
    );
  }

  removeCategory(categoryId: string): void {
    this.categories = this.categories.filter(category => category.id !== categoryId);
  }
}
