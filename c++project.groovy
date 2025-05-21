#include <iostream>
#include <vector>
#include <string>

using namespace std;

// Define a student struct
struct Student {
    int id;
    string name;
    int age;
};

// Function to insert a student
void insertStudent(vector<Student>& students) {
    Student s;
    cout << "Enter ID: ";
    cin >> s.id;
    cout << "Enter Name: ";
    cin.ignore();
    getline(cin, s.name);
    cout << "Enter Age: ";
    cin >> s.age;
    students.push_back(s);
    cout << "Student added!\n";
}

// Function to search for a student by ID
void searchStudent(const vector<Student>& students) {
    int id;
    cout << "Enter ID to search: ";
    cin >> id;
    for (const auto& s : students) {
        if (s.id == id) {
            cout << "Found: " << s.name << ", Age: " << s.age << endl;
            return;
        }
    }
    cout << "Student not found.\n";
}

// Function to delete a student by ID
void deleteStudent(vector<Student>& students) {
    int id;
    cout << "Enter ID to delete: ";
    cin >> id;
    for (auto it = students.begin(); it != students.end(); ++it) {
        if (it->id == id) {
            students.erase(it);
            cout << "Student deleted.\n";
            return;
        }
    }
    cout << "Student not found.\n";
}

// Display all students
void displayStudents(const vector<Student>& students) {
    if (students.empty()) {
        cout << "No students in the database.\n";
        return;
    }
    cout << "Student List:\n";
    for (const auto& s : students) {
        cout << "ID: " << s.id << ", Name: " << s.name << ", Age: " << s.age << endl;
    }
}

// Main menu
int main() {
    vector<Student> students;
    int choice;

    do {
        cout << "\n--- Student Database Menu ---\n";
        cout << "1. Insert Student\n2. Search Student\n3. Delete Student\n4. Display All\n5. Exit\n";
        cout << "Enter choice: ";
        cin >> choice;

        switch (choice) {
            case 1: insertStudent(students); break;
            case 2: searchStudent(students); break;
            case 3: deleteStudent(students); break;
            case 4: displayStudents(students); break;
            case 5: cout << "Goodbye!\n"; break;
            default: cout << "Invalid choice. Try again.\n";
        }
    } while (choice != 5);

    return 0;
}