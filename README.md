# 🚗 Διαχείριση Ενοικίασης Αυτοκινήτων

Το **CarRental** είναι μια εφαρμογή για τη διαχείριση ενός συστήματος ενοικίασης αυτοκινήτων. Παρέχει εργαλεία για την παρακολούθηση οχημάτων, τη διαχείριση πελατών και την καταγραφή συναλλαγών.

## ✨ Χαρακτηριστικά
- 🛠️ **Διαχείριση Στόλου Οχημάτων**: Προσθήκη οχημάτων με χαρακτηριστικά όπως πινακίδα, μάρκα, τύπος, κλπ.
- 🙋 **Διαχείριση Πελατών**: Δημιουργία και παρακολούθηση πληροφοριών πελατών.
- 📝 **Καταχώριση Ενοικιάσεων**: Καταγραφή συναλλαγών και εποπτεία διαθεσιμότητας οχημάτων.
- 📜 **Ιστορικό Ενοικιάσεων**: Ανάκτηση παλαιότερων ενοικιάσεων και επιστροφών.
- 🔄 **Επιστροφή Οχημάτων**: Διαχείριση διαδικασίας και ενημέρωση αρχείων.

## 🛠 Τεχνολογίες
- 💻 **Γλώσσα**: Java
- 📂 **Διαχείριση Αρχείων**: Χρήση CSV για αποθήκευση δεδομένων.
- 📅 **Χρόνος και Ημερομηνίες**: Ενσωμάτωση της `LocalDate` για τη διαχείριση συναλλαγών.

## 📂 Δομή Directory
```plaintext
CarRental/
├── src/
│   ├── api/
│   │   ├── Car.java       # Κλάση για τη διαχείριση πληροφοριών οχημάτων
│   │   ├── Rental.java    # Κλάση για τη διαχείριση συναλλαγών ενοικίασης
│   ├── filemanager/
│   │   ├── Paths.java     # Διαδρομές αρχείων (CSV)
│   │   ├── Reader.java    # Βοηθητικό εργαλείο για ανάγνωση CSV
│   │   ├── Writer.java    # Εργαλείο εγγραφής σε CSV
│   │   ├── UpdateCar.java # Ενημέρωση κατάστασης οχημάτων
│   ├── gui/
│   │   ├── CarsPanel.java     
│   │   ├── CustomerPanel.java    
│   │   ├── DashBoard.java   
│   │   ├── loginWindow.java 
```

---

# 🚗 Car Rental Management

The **CarRental** project is a Java-based application designed for managing a car rental system. It offers tools for monitoring vehicles, managing customers, and logging transactions.

## ✨ Features
- 🛠️ **Fleet Management**: Add vehicles with details like plate numbers, brand, type, etc.
- 🙋 **Customer Management**: Create and track customer profiles.
- 📝 **Rental Logging**: Record transactions and monitor vehicle availability.
- 📜 **Rental History**: Retrieve and inspect historic rentals and returns.
- 🔄 **Vehicle Returns**: Manage return processes and update records.

## 🛠 Technologies
- 💻 **Language**: Java
- 📂 **Data Storage**: CSV files for persisting information.
- 📅 **Date Handling**: Utilizes `LocalDate` for transactions.

## 📂 Project Structure
```plaintext
CarRental/
├── src/
│   ├── api/
│   │   ├── Car.java       # Class to manage vehicle properties
│   │   ├── Rental.java    # Class to handle rental transactions
│   ├── filemanager/
│   │   ├── Paths.java     # Paths for CSV files
│   │   ├── Reader.java    # Utility for reading CSV files
│   │   ├── Writer.java    # Utility for writing to CSV files
│   │   ├── UpdateCar.java # Updates vehicle statuses
│   ├── gui/
│   │   ├── CarsPanel.java     
│   │   ├── CustomerPanel.java    
│   │   ├── DashBoard.java   
│   │   ├── loginWindow.java 
```
