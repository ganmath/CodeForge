export const metadata = {
  title: 'CodeForge Platform',
  description: 'Enterprise Full-Stack Development Platform with Spring Boot, Next.js, and AWS',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  )
}
