import { Component, OnInit } from '@angular/core';
import {User} from "../user";
import {UserService} from "../user-service.service";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  constructor(private userService: UserService) { }

  users: User[];

  ngOnInit(): void {
    this.userService.findAll().subscribe(data=>{
      this.users = data;
    });
  }

}
