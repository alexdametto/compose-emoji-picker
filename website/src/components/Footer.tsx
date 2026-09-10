import styles from "./Footer.module.scss";

const START_YEAR = 2025;

export function Footer() {
  const currentYear = new Date().getFullYear();
  const years = currentYear > START_YEAR ? `${START_YEAR}–${currentYear}` : `${START_YEAR}`;

  return (
    <footer className={styles.footer}>
      <div className="container">
        <div className={styles.grid}>
          <div className={styles.copy}>
            <h2 className={styles.heading}>Contribute</h2>
            <p className={styles.body}>
              If you&apos;d like to contribute to this library, feel free to
              open a pull request or report an issue. Any contribution is
              welcome — including new search languages.
            </p>
          </div>
          <div className={styles.links}>
            <a href="https://github.com/alexdametto/compose-emoji-picker">
              Repository
            </a>
            <a href="https://github.com/alexdametto/compose-emoji-picker/issues">
              Report an issue
            </a>
            <a href="https://github.com/alexdametto/compose-emoji-picker/pulls">
              Open a pull request
            </a>
          </div>
        </div>
        <div className={styles.legal}>
          <span>MIT License · © {years} Alex Dametto</span>
          <span>Built with Jetpack Compose</span>
        </div>
      </div>
    </footer>
  );
}
