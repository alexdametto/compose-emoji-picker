import styles from "./SearchHighlight.module.scss";

export function SearchHighlight() {
  return (
    <section className={`section ${styles.section}`}>
      <div className={`container ${styles.grid}`}>
        <div className={styles.copy}>
          <span className="eyebrow">Search, not just display</span>
          <h2 className={styles.heading}>Search that matches how the device talks</h2>
          <p className={styles.body}>
            Most pickers translate the emoji labels but still search in
            English only. This one matches the query against the
            device&apos;s own language, so typing in Italian finds the same
            emoji typing in English would.
          </p>
          <span className={styles.note}>
            <strong>Today:</strong> English and Italian — more languages land
            through pull requests.
          </span>
        </div>

        <div className={styles.compare} aria-hidden="true">
          <div className={styles.locale}>
            <span className={styles.localeTag}>device locale: it-IT</span>
            <span className={styles.query}>
              <span>search “</span>
              <strong>cane</strong>
              <span>”</span>
            </span>
            <div className={styles.results}>
              <span>🐶</span>
              <span>🐕</span>
              <span>🦮</span>
            </div>
          </div>
          <div className={styles.locale}>
            <span className={styles.localeTag}>device locale: en-US</span>
            <span className={styles.query}>
              <span>search “</span>
              <strong>dog</strong>
              <span>”</span>
            </span>
            <div className={styles.results}>
              <span>🐶</span>
              <span>🐕</span>
              <span>🦮</span>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
