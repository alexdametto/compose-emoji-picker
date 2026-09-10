import type { Metadata } from "next";
import { Analytics } from "@vercel/analytics/next";
import { SpeedInsights } from "@vercel/speed-insights/next";
import "./globals.scss";
import { ThemeToggle } from "@/components/ThemeToggle";
import { SITE_URL, SITE_NAME, SITE_DESCRIPTION, REPO_URL } from "@/lib/site";

const themeInitScript = `
try {
  var t = localStorage.getItem("theme");
  if (t === "light" || t === "dark") {
    document.documentElement.setAttribute("data-theme", t);
  }
} catch (e) {}
`;

export const metadata: Metadata = {
  metadataBase: new URL(SITE_URL),
  title: SITE_NAME,
  description: SITE_DESCRIPTION,
  keywords: [
    "Jetpack Compose",
    "emoji picker",
    "Android library",
    "Kotlin",
    "Material 3",
    "Compose Multiplatform",
  ],
  authors: [{ name: "Alex Dametto", url: "https://github.com/alexdametto" }],
  alternates: {
    canonical: "/",
  },
  openGraph: {
    title: SITE_NAME,
    description: SITE_DESCRIPTION,
    url: SITE_URL,
    siteName: SITE_NAME,
    type: "website",
    images: [
      {
        url: "/og-image.png",
        width: 1200,
        height: 630,
        alt: SITE_NAME,
      },
    ],
  },
  twitter: {
    card: "summary_large_image",
    title: SITE_NAME,
    description: SITE_DESCRIPTION,
    images: ["/og-image.png"],
  },
};

const jsonLd = {
  "@context": "https://schema.org",
  "@type": "SoftwareSourceCode",
  name: SITE_NAME,
  description: SITE_DESCRIPTION,
  codeRepository: REPO_URL,
  programmingLanguage: "Kotlin",
  runtimePlatform: "Jetpack Compose",
  license: "https://opensource.org/licenses/MIT",
  author: {
    "@type": "Person",
    name: "Alex Dametto",
    url: "https://github.com/alexdametto",
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" suppressHydrationWarning>
      <head>
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link
          rel="preconnect"
          href="https://fonts.gstatic.com"
          crossOrigin="anonymous"
        />
        <link
          href="https://fonts.googleapis.com/css2?family=Fraunces:opsz,wght@9..144,500;9..144,600;9..144,700&family=Manrope:wght@400;500;600;700&family=JetBrains+Mono:wght@400;500;600&display=swap"
          rel="stylesheet"
        />
        <script dangerouslySetInnerHTML={{ __html: themeInitScript }} />
        <script
          type="application/ld+json"
          dangerouslySetInnerHTML={{ __html: JSON.stringify(jsonLd) }}
        />
      </head>
      <body>
        <ThemeToggle />
        {children}
        <Analytics />
        <SpeedInsights />
      </body>
    </html>
  );
}
