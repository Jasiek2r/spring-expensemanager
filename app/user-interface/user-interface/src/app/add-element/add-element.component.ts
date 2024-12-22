import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-element',
  templateUrl: './add-element.component.html',
  styleUrls: ['./add-element.component.css']
})
export class AddElementComponent {
  // Inject HttpClient
  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) {}

  submitForm(event: Event): void {
    event.preventDefault(); // Prevent default form submission behavior

    // Collect form values
    const form = event.target as HTMLFormElement;
    const elementName = (form.querySelector('#elementName') as HTMLInputElement).value;
    const elementDescription = (form.querySelector('#elementDescription') as HTMLInputElement).value;
    const elementValue = parseFloat((form.querySelector('#elementValue') as HTMLInputElement).value);

    // Create the payload
    const payload = {
      name: elementName,
      description: elementDescription,
      amount: elementValue,
      categoryId: this.route.snapshot.paramMap.get('id')
    };

    // Send POST request
    this.http.post('http://localhost:8083/expenses/new', payload).subscribe({
      next: (response) => {
        console.log('Element added successfully:', response);
      },
      error: (error) => {
        console.error('Error adding element:', error);
      }
    });
    this.router.navigate(['./..']);
  }
}
