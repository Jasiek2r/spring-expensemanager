import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';  // Import CommonModule
import { AddCategoryComponent } from '../add-category/add-category.component';
import { Router } from '@angular/router';

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

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.fetchCategories();
  }

  fetchCategories(): void {
    this.http.get<CategoriesResponse>('http://localhost:8083/categories/').subscribe(
      (data) => {
        this.categories = data.expenseCategories;
      },
      (error) => {
        console.error('Error fetching categories', error);
      }
    );
  }

  removeCategory(categoryId: string): void {
    if(window.confirm('Are sure you want to delete this item ?')){
      this.categories = this.categories.filter(category => category.id !== categoryId);
    }
    this.http.delete(`http://localhost:8083/categories/delete/${categoryId}`).subscribe(
      () => {
        console.log(`Category with id ${categoryId} deleted from backend`);
      },
      (error) => {
        console.error('Error deleting category from backend', error);
      }
    );
  }
  addNewCategory(): void{
    this.router.navigate(['add']);
  }
  editCategory(categoryId: string) : void{
    window.location.href = 'http://localhost:4200/categories/edit/'+categoryId;
  }
  viewCategory(categoryId: string) : void{
    window.location.href = 'http://localhost:4200/categories/'+categoryId;
  }
  
}
