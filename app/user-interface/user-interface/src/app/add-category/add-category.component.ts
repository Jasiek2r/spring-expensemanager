import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent {
  // Inject HttpClient
  constructor(private http: HttpClient, private router: Router) {}

  submitForm(event: Event): void {
    event.preventDefault(); // Prevent default form submission behavior

    // Collect form values
    const form = event.target as HTMLFormElement;
    const categoryName = (form.querySelector('#categoryName') as HTMLInputElement).value;
    const categoryDescription = (form.querySelector('#categoryDescription') as HTMLInputElement).value;
    const categoryBudget = parseFloat((form.querySelector('#categoryBudget') as HTMLInputElement).value);

    // Create the payload
    const payload = {
      name: categoryName,
      description: categoryDescription,
      budget: categoryBudget
    };

    // Send POST request
    this.http.post('http://localhost:8083/categories/new', payload).subscribe({
      next: (response) => {
        console.log('Category added successfully:', response);
      },
      error: (error) => {
        console.error('Error adding category:', error);
      }
    });
    window.location.href = 'http://localhost:4200/categories';
  }
}
