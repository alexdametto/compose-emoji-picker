"use client";

import { useEffect, useState } from "react";

const REPO = "alexdametto/compose-emoji-picker";

function formatStars(n: number) {
  return n >= 1000 ? `${(n / 1000).toFixed(1)}k` : `${n}`;
}

export function RepoStats() {
  const [stars, setStars] = useState<number | null>(null);
  const [version, setVersion] = useState<string | null>(null);

  useEffect(() => {
    let cancelled = false;

    fetch(`https://api.github.com/repos/${REPO}`)
      .then((res) => (res.ok ? res.json() : null))
      .then((data) => {
        if (!cancelled && data?.stargazers_count != null) {
          setStars(data.stargazers_count);
        }
      })
      .catch(() => {});

    fetch(`https://api.github.com/repos/${REPO}/releases/latest`)
      .then((res) => (res.ok ? res.json() : null))
      .then((data) => {
        if (!cancelled && data?.tag_name) {
          setVersion(data.tag_name.replace(/^v/, ""));
        }
      })
      .catch(() => {});

    return () => {
      cancelled = true;
    };
  }, []);

  return (
    <>
      {stars !== null && (
        <span>
          <strong>★ {formatStars(stars)}</strong> on GitHub
        </span>
      )}
      {version !== null && (
        <span>
          <strong>v{version}</strong> latest
        </span>
      )}
    </>
  );
}
