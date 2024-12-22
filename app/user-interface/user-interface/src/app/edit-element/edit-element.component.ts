import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface Element {
  id: string;
  name: string;
  description: string;
  amount: number;
}

@Component({
  selector: 'app-edit-element',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './edit-element.component.html',
  styleUrls: ['./edit-element.component.css'],
})
export class EditElementComponent implements OnInit {
  elementForm!: FormGroup;

  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.initializeForm();

    const id = this.route.snapshot.paramMap.get('elementId');
    if (id) {
      this.fetchElement(id);  // Fetch element details by ID
    } else {
      throw new Error('Element ID has not been provided');
    }
  }

  // Initialize the form with controls
  initializeForm(): void {
    this.elementForm = this.fb.group({
      id: [{ value: '', disabled: true }],
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: [''],
      amount: [0, [Validators.required, Validators.min(0.01)]],  // Ensure amount is positive
    });
  }

  // Fetch element details by ID
  fetchElement(elementId: string): void {
    this.http.get<Element>(`http://localhost:8083/expenses/${elementId}`).subscribe(
      (data) => {
        console.log(data);
        this.elementForm.patchValue(data);  // Autofill the form with the fetched data
      },
      (error) => {
        console.error('Error fetching element', error);
      }
    );
  }

  // Handle form submission
  onSubmit(): void {
    if (this.elementForm.valid) {
      const updatedElement = this.elementForm.getRawValue();  // Get form values (including disabled ones)
      this.updateElement(updatedElement);
    }
  }

  // Send updated data to server
  updateElement(element: Element): void {
    this.http.patch(`http://localhost:8083/expenses/update/${element.id}`, element).subscribe(
      () => {
        alert('Element updated successfully!');
        this.router.navigate(['./../..']);  // Redirect to elements list after successful update
      },
      (error) => {
        console.error('Error updating element', error);
      }
    );
  }
}
