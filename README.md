# RestPDF

[![License: AGPL v3](https://img.shields.io/badge/License-AGPL_v3-blue.svg)](https://www.gnu.org/licenses/agpl-3.0)

RestPDF is a self-hostable, open-source RESTful API service for handling PDF document operations. Built with Scala and the Play Framework, it provides a robust backend for your PDF signing and verification workflows.

---

## Table of Contents

- [What does it do?](#what-does-it-do)
- [Why is it useful?](#why-is-it-useful)
- [How to Get Started](#how-to-get-started)
  - [Prerequisites](#prerequisites)
  - [Deployment Steps](#deployment-steps)
- [License](#license)
- [Where to Get Help](#where-to-get-help)
- [Contributing](#contributing)

---

### What does it do?

RestPDF offers a suite of API endpoints for common and advanced PDF tasks, including:

* **Digital Signatures:** Applying digital signatures to PDF documents.
* **Signature Verification:** Verifying the authenticity and integrity of signatures within a PDF.
* **Custom Signature Appearances:** Customizing the visual representation of digital signatures.
* **Advanced PDF/A-3 Signing:** Support for specialized, long-term archival formats.

### Why is it useful?

In a world of cloud services, RestPDF provides a critical alternative for organizations that need full control over their document processing workflows.

* **Self-Hosting & Control:** Deploy RestPDF on your own infrastructure, ensuring your sensitive documents never leave your control.
* **Open Source & Transparent:** The entire codebase is available for you to inspect, modify, and enhance. No black boxes.
* **Cost-Effective:** Avoid per-document or per-API-call fees associated with commercial services.
* **Scalable:** Built on a modern, non-blocking technology stack (Play Framework, Akka), it's designed for high-performance and concurrent workloads.

### How to Get Started

Follow these steps to get a local instance of RestPDF up and running.

#### Prerequisites

Ensure you have the following software installed on your system:

* **Java Development Kit (JDK):** Version 8 or higher.
* **sbt (Simple Build Tool):** The primary build tool for Scala projects.

#### Deployment Steps

1.  **Clone the Repository**
    Get the source code from the official GitHub repository.
    ```bash
    git clone [https://github.com/Product-BU/restpdf.git](https://github.com/Product-BU/restpdf.git)
    cd restpdf
    ```

2.  **Build the Application**
    Use `sbt` to compile the source code and package it into a distributable format.
    ```bash
    sbt dist
    ```
    This command will create a `.zip` file (e.g., `restpdf-1.0.zip`) in the `target/universal/` directory.

3.  **Unzip the Package**
    Extract the application from the archive created in the previous step.
    ```bash
    unzip target/universal/restpdf-1.0.zip
    ```

4.  **Run the Application**
    Navigate into the extracted directory and use the provided binary script to start the service. You must provide a secret key and specify a configuration file.
    ```bash
    # Example: cd restpdf-1.0/
    bin/restpdf -Dplay.http.secret.key='your-super-secret-key' -Dconfig.file=conf/prod.conf -Dpidfile.path=/dev/null
    ```
    **Important:**
    * Replace `'your-super-secret-key'` with a long, unique, and random string. This is crucial for securing user sessions.
    * You may need to create a `conf/prod.conf` file for your production settings or modify the existing `conf/common.conf`.

### License

This project is licensed under the **GNU Affero General Public License v3.0 (AGPL-3.0)**.

A primary reason for this is our use of the powerful **iText library** for PDF manipulation, which is also distributed under the AGPL v3.0. In compliance with its terms, RestPDF adopts the same license. This ensures that any user interacting with the RestPDF service over a network has the right to receive the complete source code of the version they are using.

You can find the full license text in the `LICENSE` file in this repository.

### Where to Get Help

If you encounter any issues, have questions, or need help with the project, please use one of the following channels:

* **GitHub Issues:** For bug reports, feature requests, or technical questions, please [open an issue](https://github.com/Product-BU/restpdf/issues) on our GitHub repository.
* **Email:** For other inquiries, you can contact us at **product@brainergy.digital**.

### Contributing

We welcome contributions from the community! Whether it's improving the documentation, fixing a bug, or adding a new feature, your help is appreciated. Please feel free to fork the repository and submit a pull request.

This project is maintained by the team at **product@brainergy.digital**.
