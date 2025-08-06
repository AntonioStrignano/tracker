Personal Finance Tracker project is to empower individuals to effectively manage their personal finances with great tools and capabilities. This platform will provide users to take control of their financial activities, monitor their financial health, and can track their expenses and income. With the help of this platform, a user can understand their financial position, and identify areas of improvement, so that they can make informed decisions about their spending and saving habits.

The Personal Finance Tracker aims to simplify financial management for users. This application will provide users with a user-friendly platform where users can input their income and expenses, users can categorize transactions, and can uncover patterns in spending behaviors. This platform provides a Data Categorisation that will give users a clear picture of where their money is going, this aids identifying areas for potential budget adjustments.

Visual Analytics: Add pie charts, bar graphs, and other visual representations of the user’s financial status to make it more user-friendly.
Automated Expense Categorization: Integrate with APIs (like Plaid or Yodlee) to fetch bank transactions automatically and categorize expenses.
Budgeting Feature: Implement a budgeting feature where users can set a budget for different categories and receive alerts when they are approaching the budget limits.


FEATURES DA IMPLEMENTARE NEL NOSTRO TRACKER
🎯 MUST-HAVE (Core Features)
Quick Add - Aggiunta veloce transazioni (4 tap max)
Smart Categories - Categorie predefinite + personalizzabili
Monthly Overview - Budget vs Speso (% completion)
Visual Flow - Come scorrono i soldi (ispirato Toshl)
"Pocket Money" - Quanto posso spendere oggi (ispirato PocketGuard)
🚀 NICE-TO-HAVE (Advanced)
Photo Receipts - Upload foto scontrini
Recurring Transactions - Stipendio, bollette automatiche
Goal Tracking - Obiettivi di risparmio
Export Data - CSV/PDF reports
Dark Mode - UI moderna
📊 ANALYTICS & CHARTS
Pie Chart - Spese per categoria
Bar Chart - Trend mensile
Line Chart - Net worth over time
Heatmap - Spending by day/week

Back-end
entities/
├── Transaction (amount, date, category, description, type)
├── Category (name, color, icon, type, budget_limit)
├── RecurringTransaction (template per spese fisse)
└── Goal (target_amount, current_amount, deadline)

controllers/
├── DashboardController (overview + charts)
├── TransactionController (CRUD + quick-add)
├── CategoryController (management)
└── AnalyticsController (reports + insights)

Front-end
templates/
├── dashboard.html (overview + quick stats)
├── transactions/
│   ├── list.html (filterable list)
│   ├── add.html (quick form)
│   └── bulk-edit.html
├── analytics/
│   ├── charts.html (visual reports)
│   └── insights.html (spending patterns)
└── settings/
    ├── categories.html
    └── goals.html

    UI/UX PRINCIPLES (Best Practices)
1. Speed First (da PocketGuard)
⚡ 3-tap rule - Max 3 click per aggiungere transazione
⚡ Smart defaults - Categoria più usata pre-selezionata
⚡ Voice input per importi
2. Visual Clarity (da Toshl)
🎨 Color coding per categorie
📊 Progressive disclosure - mostra dettagli on-demand
🌊 Flow visualization - dove vanno i soldi
3. Actionable Insights (da YNAB)
💡 Budget alerts prima di sforare
💡 Spending patterns - "Spendi 40% in più nei weekend"
💡 Suggestions - "Puoi risparmiare €50 su caffè"
