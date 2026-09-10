import styles from "./Hero.module.scss";
import { RepoStats } from "./RepoStats";

export function Hero() {
  return (
    <section className={styles.hero}>
      <div className={`container ${styles.grid}`}>
        <div className={styles.copy}>
          <span className="eyebrow">Jetpack Compose · Android library</span>
          <h1 className={styles.title}>
            An emoji picker that <em>speaks your user&apos;s language</em>.
          </h1>
          <p className={styles.lede}>
            Compose Emoji Picker drops a full Unicode picker into any screen —
            search matched to the device&apos;s language, recents remembered,
            colors pulled straight from your Material 3 theme.
          </p>
          <div className={styles.ctaRow}>
            <a
              className={styles.ctaPrimary}
              href="https://github.com/alexdametto/compose-emoji-picker"
            >
              View on GitHub
            </a>
            <a className={styles.ctaSecondary} href="#install">
              Jump to install
            </a>
          </div>
          <div className={styles.meta}>
            <span>
              <strong>2</strong> search locales — EN / IT
            </span>
            <span>
              <strong>MIT</strong> licensed
            </span>
            <RepoStats />
          </div>
        </div>

        <div className={styles.visual}>
          <img
            className={styles.lightImg}
            src="/images/hero_light.png"
            width={1080}
            height={970}
            alt="The EmojiPicker composable open over an app, showing search, recents, category tabs and the Smileys &amp; People grid"
          />
          <img
            className={styles.darkImg}
            src="/images/hero_dark.png"
            width={1080}
            height={970}
            alt=""
            aria-hidden="true"
          />
        </div>
      </div>
    </section>
  );
}
