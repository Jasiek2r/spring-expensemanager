import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface Category {
  id: string;
  name: string;
  description: string;
  budget: number;
}

@Component({
  selector: 'app-edit-category',
  templateUrl: './edit-category.component.html',
  styleUrls: ['./edit-category.component.css'],
  imports: [CommonModule, ReactiveFormsModule]
})
export class EditCategoryComponent implements OnInit {
  categoryForm!: FormGroup;

  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.initializeForm();

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.fetchCategory(id);
    } else {
      throw new Error('id has not been provided');
    }
  }

  initializeForm(): void {
    this.categoryForm = this.fb.group({
      id: [{ value: '', disabled: true }],
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: [''],
      budget: [0, [Validators.required, Validators.min(0)]],
    });
  }

  fetchCategory(categoryId: string): void {
    this.http.get<Category>(`http://localhost:8083/categories/${categoryId}`).subscribe(
      (data) => {
        this.categoryForm.patchValue(data); // Populate the form with fetched data
      },
      (error) => {
        console.error('Error fetching category', error);
      }
    );
  }

  onSubmit(): void {
    if (this.categoryForm.valid) {
      const updatedCategory = this.categoryForm.getRawValue(); // Get all values, including disabled ones
      this.updateCategory(updatedCategory);
    }
  }

  updateCategory(category: Category): void {
    this.http.patch(`http://localhost:8083/categories/update/${category.id}`, category).subscribe(
      () => {
        alert('Category updated successfully!');
      },
      (error) => {
        console.error('Error updating category', error);
      }
    );
    window.location.href = 'http://localhost:4200/categories';

  }
}
