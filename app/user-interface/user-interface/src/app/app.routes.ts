import { Routes } from '@angular/router';
import { CategoriesComponent } from './categories/categories.component';
import { AddCategoryComponent } from './add-category/add-category.component';
import { EditCategoryComponent } from './edit-category/edit-category.component';
import { CategoryDetailsComponent } from './category-details/category-details.component';
import { AddElementComponent } from './add-element/add-element.component';
import { EditElementComponent } from './edit-element/edit-element.component';
import { ElementDetailsComponent } from './element-details/element-details.component';

export const routes: Routes = [
  { path: '', redirectTo: '/categories', pathMatch: 'full' },
  { 
    path: 'categories', 
    component: CategoriesComponent,
    children: [
      { 
        path: 'add', 
        component: AddCategoryComponent, 
        outlet: 'addCategory' 
      }
    ]
  },
  { path: 'categories/add', pathMatch: 'full', component: AddCategoryComponent },
  { path: 'categories/edit/:id', component: EditCategoryComponent },
  { path: 'categories/:id', component: CategoryDetailsComponent },
  { path: 'categories/:id/add-element', component: AddElementComponent },
  { path: 'categories/:id/edit-element/:elementId', component: EditElementComponent },
  { path: 'categories/:id/elements/:elementId', component: ElementDetailsComponent },
];
