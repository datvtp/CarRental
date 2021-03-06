USE [master]
GO
/****** Object:  Database [Car]    Script Date: 4/11/2020 6:08:05 PM ******/
CREATE DATABASE [Car]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Car', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.SQLEXPRESS\MSSQL\DATA\Car.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Car_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.SQLEXPRESS\MSSQL\DATA\Car_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [Car] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Car].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Car] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Car] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Car] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Car] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Car] SET ARITHABORT OFF 
GO
ALTER DATABASE [Car] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Car] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Car] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Car] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Car] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Car] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Car] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Car] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Car] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Car] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Car] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Car] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Car] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Car] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Car] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Car] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Car] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Car] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [Car] SET  MULTI_USER 
GO
ALTER DATABASE [Car] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Car] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Car] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Car] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Car] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Car] SET QUERY_STORE = OFF
GO
USE [Car]
GO
/****** Object:  Table [dbo].[tbl_Car]    Script Date: 4/11/2020 6:08:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Car](
	[CarID] [int] IDENTITY(1,1) NOT NULL,
	[Image] [nvarchar](50) NULL,
	[Name] [nvarchar](50) NULL,
	[Color] [nvarchar](50) NULL,
	[Year] [int] NULL,
	[Price] [int] NULL,
	[Quantity] [int] NULL,
	[CategoryID] [int] NULL,
	[StatusID] [int] NULL,
 CONSTRAINT [PK_tbl_Car] PRIMARY KEY CLUSTERED 
(
	[CarID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Category]    Script Date: 4/11/2020 6:08:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Category](
	[CategoryID] [int] NOT NULL,
	[CategoryName] [nvarchar](50) NULL,
 CONSTRAINT [PK_tbl_Category] PRIMARY KEY CLUSTERED 
(
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Discount]    Script Date: 4/11/2020 6:08:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Discount](
	[DiscountID] [int] IDENTITY(1,1) NOT NULL,
	[Code] [nvarchar](50) NULL,
	[DiscountPercent] [int] NULL,
	[ExpiryTime] [datetime] NULL,
	[StatusID] [int] NULL,
 CONSTRAINT [PK_tbl_Discount] PRIMARY KEY CLUSTERED 
(
	[DiscountID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Invoice]    Script Date: 4/11/2020 6:08:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Invoice](
	[InvoiceID] [int] IDENTITY(1,1) NOT NULL,
	[CreateTime] [datetime] NULL,
	[TotalPrice] [int] NULL,
	[Email] [nvarchar](50) NULL,
	[DiscountID] [int] NULL,
	[StatusID] [int] NULL,
 CONSTRAINT [PK_tbl_Invoice] PRIMARY KEY CLUSTERED 
(
	[InvoiceID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_InvoiceDetail]    Script Date: 4/11/2020 6:08:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_InvoiceDetail](
	[InvoiceDetailID] [int] IDENTITY(1,1) NOT NULL,
	[CarID] [int] NULL,
	[RentalDate] [date] NULL,
	[ReturnDate] [date] NULL,
	[Quantity] [int] NULL,
	[TotalPrice] [int] NULL,
	[InvoiceID] [int] NULL,
 CONSTRAINT [PK_tbl_InvoiceDetail] PRIMARY KEY CLUSTERED 
(
	[InvoiceDetailID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Role]    Script Date: 4/11/2020 6:08:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Role](
	[RoleID] [int] NOT NULL,
	[RoleName] [nvarchar](50) NULL,
 CONSTRAINT [PK_tbl_Role] PRIMARY KEY CLUSTERED 
(
	[RoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Status]    Script Date: 4/11/2020 6:08:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Status](
	[StatusID] [int] NOT NULL,
	[StatusName] [nvarchar](50) NULL,
 CONSTRAINT [PK_tbl_Status] PRIMARY KEY CLUSTERED 
(
	[StatusID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_User]    Script Date: 4/11/2020 6:08:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_User](
	[Email] [nvarchar](50) NOT NULL,
	[Phone] [nvarchar](50) NULL,
	[Name] [nvarchar](50) NULL,
	[Address] [nvarchar](50) NULL,
	[CreateTime] [datetime] NULL,
	[Password] [nvarchar](50) NULL,
	[RoleID] [int] NULL,
	[StatusID] [int] NULL,
 CONSTRAINT [PK_tbl_User] PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[tbl_Car] ON 

INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (1, N'bumblebee.jpg', N'Bumblebee', N'Yellow', 2000, 100, 50, 1, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (2, N'trailblazer.jpg', N'Trailblazer', N'White', 2017, 120, 50, 1, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (3, N'colorado.jpg', N'Colorado', N'Blackk', 2018, 110, 50, 1, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (4, N'spark.jpg', N'Spark', N'White', 2020, 80, 50, 1, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (5, N'aveo.jpg', N'Aveo', N'White', 2015, 90, 50, 1, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (6, N'mercedesVClass.jpg', N'V Class', N'White', 2020, 115, 50, 2, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (7, N'mercedesSClass.jpg', N'S Class', N'White', 2020, 130, 50, 2, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (8, N'mercedesCClass.jpg', N'C Class', N'White', 2020, 140, 50, 2, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (9, N'mercedesEClass.jpg', N'E Class', N'Black', 2020, 150, 50, 2, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (10, N'mercedesmaybach.jpg', N'Maybach', N'White', 2020, 160, 50, 2, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (11, N'fordecosport.jpg', N'Eco Sport', N'Red', 2018, 170, 50, 3, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (12, N'fordtourneo.jpg', N'Tourneo', N'Black', 2018, 175, 50, 3, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (13, N'fordranger.jpg', N'Ranger', N'Yellow', 2018, 180, 50, 3, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (14, N'fordeverest.jpg', N'Everest', N'Blue', 2018, 185, 50, 3, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (15, N'fordtransit.jpg', N'Transit', N'Blue', 2018, 190, 50, 3, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (16, N'laferrari.jpg', N'LA', N'Black', 2019, 200, 50, 4, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (17, N'ferrarif8.jpg', N'F8', N'Yellow', 2019, 210, 50, 4, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (18, N'ferrari812.jpg', N'812', N'Red', 2019, 220, 50, 4, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (19, N'ferrarisf90.jpg', N'SF90', N'Red', 2019, 230, 50, 4, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (20, N'ferrarif12.jpg', N'F12', N'Red', 2019, 240, 50, 4, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (21, N'lamborghini-sian.jpg', N'Sian', N'Yellow', 2021, 250, 50, 5, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (22, N'lamborghinihuracan.jpg', N'Huracan', N'Yellow', 2021, 260, 50, 5, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (23, N'lamborghiniaventadorsvj.jpg', N'SVJ', N'Green', 2021, 270, 50, 5, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (24, N'lamborghiniaventadors.jpg', N'Aventador S', N'Orange', 2021, 280, 50, 5, 2)
INSERT [dbo].[tbl_Car] ([CarID], [Image], [Name], [Color], [Year], [Price], [Quantity], [CategoryID], [StatusID]) VALUES (25, N'lamborghiniurus.jpg', N'Urus', N'Yellow', 2021, 290, 50, 5, 2)
SET IDENTITY_INSERT [dbo].[tbl_Car] OFF
INSERT [dbo].[tbl_Category] ([CategoryID], [CategoryName]) VALUES (1, N'Chevrolet')
INSERT [dbo].[tbl_Category] ([CategoryID], [CategoryName]) VALUES (2, N'Mercedes')
INSERT [dbo].[tbl_Category] ([CategoryID], [CategoryName]) VALUES (3, N'Ford')
INSERT [dbo].[tbl_Category] ([CategoryID], [CategoryName]) VALUES (4, N'Ferrari')
INSERT [dbo].[tbl_Category] ([CategoryID], [CategoryName]) VALUES (5, N'Lamborghini')
SET IDENTITY_INSERT [dbo].[tbl_Discount] ON 

INSERT [dbo].[tbl_Discount] ([DiscountID], [Code], [DiscountPercent], [ExpiryTime], [StatusID]) VALUES (1, N'AHIHI', 70, CAST(N'2020-05-01T00:00:00.000' AS DateTime), 2)
INSERT [dbo].[tbl_Discount] ([DiscountID], [Code], [DiscountPercent], [ExpiryTime], [StatusID]) VALUES (2, N'AHUHU', 50, CAST(N'2020-05-01T00:00:00.000' AS DateTime), 2)
INSERT [dbo].[tbl_Discount] ([DiscountID], [Code], [DiscountPercent], [ExpiryTime], [StatusID]) VALUES (3, N'AHAHA', 30, CAST(N'2020-05-01T00:00:00.000' AS DateTime), 2)
SET IDENTITY_INSERT [dbo].[tbl_Discount] OFF
SET IDENTITY_INSERT [dbo].[tbl_Invoice] ON 

INSERT [dbo].[tbl_Invoice] ([InvoiceID], [CreateTime], [TotalPrice], [Email], [DiscountID], [StatusID]) VALUES (1, CAST(N'2020-03-23T16:13:24.000' AS DateTime), 10000, N'vanthanhphuongdat1309@gmail.com', NULL, 2)
INSERT [dbo].[tbl_Invoice] ([InvoiceID], [CreateTime], [TotalPrice], [Email], [DiscountID], [StatusID]) VALUES (2, CAST(N'2020-03-23T16:14:40.000' AS DateTime), 2500, N'vanthanhphuongdat1309@gmail.com', NULL, 2)
SET IDENTITY_INSERT [dbo].[tbl_Invoice] OFF
SET IDENTITY_INSERT [dbo].[tbl_InvoiceDetail] ON 

INSERT [dbo].[tbl_InvoiceDetail] ([InvoiceDetailID], [CarID], [RentalDate], [ReturnDate], [Quantity], [TotalPrice], [InvoiceID]) VALUES (1, 21, CAST(N'2020-03-23' AS Date), CAST(N'2020-03-24' AS Date), 40, 10000, 1)
INSERT [dbo].[tbl_InvoiceDetail] ([InvoiceDetailID], [CarID], [RentalDate], [ReturnDate], [Quantity], [TotalPrice], [InvoiceID]) VALUES (2, 21, CAST(N'2020-03-23' AS Date), CAST(N'2020-03-24' AS Date), 10, 2500, 2)
SET IDENTITY_INSERT [dbo].[tbl_InvoiceDetail] OFF
INSERT [dbo].[tbl_Role] ([RoleID], [RoleName]) VALUES (1, N'admin')
INSERT [dbo].[tbl_Role] ([RoleID], [RoleName]) VALUES (2, N'user')
INSERT [dbo].[tbl_Status] ([StatusID], [StatusName]) VALUES (1, N'new')
INSERT [dbo].[tbl_Status] ([StatusID], [StatusName]) VALUES (2, N'active')
INSERT [dbo].[tbl_Status] ([StatusID], [StatusName]) VALUES (3, N'deleted')
INSERT [dbo].[tbl_User] ([Email], [Phone], [Name], [Address], [CreateTime], [Password], [RoleID], [StatusID]) VALUES (N'admin@gmail.com', N'0123456789', N'ADMIN', N'Tây Ninh', NULL, N'12345', 1, 2)
INSERT [dbo].[tbl_User] ([Email], [Phone], [Name], [Address], [CreateTime], [Password], [RoleID], [StatusID]) VALUES (N'user@gmail.com', N'0123456789', N'USER', N'Tây Ninh', NULL, N'12345', 2, 2)
INSERT [dbo].[tbl_User] ([Email], [Phone], [Name], [Address], [CreateTime], [Password], [RoleID], [StatusID]) VALUES (N'vanthanhphuongdat1309@gmail.com', N'0123456789', N'Phương Đạt', N'Tay Ninh', CAST(N'2020-03-23T10:41:48.000' AS DateTime), N'12345', 2, 2)
/****** Object:  Index [unique_price]    Script Date: 4/11/2020 6:08:05 PM ******/
ALTER TABLE [dbo].[tbl_Car] ADD  CONSTRAINT [unique_price] UNIQUE NONCLUSTERED 
(
	[Price] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [unique_code]    Script Date: 4/11/2020 6:08:05 PM ******/
ALTER TABLE [dbo].[tbl_Discount] ADD  CONSTRAINT [unique_code] UNIQUE NONCLUSTERED 
(
	[Code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[tbl_Car]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Car_tbl_Category] FOREIGN KEY([CategoryID])
REFERENCES [dbo].[tbl_Category] ([CategoryID])
GO
ALTER TABLE [dbo].[tbl_Car] CHECK CONSTRAINT [FK_tbl_Car_tbl_Category]
GO
ALTER TABLE [dbo].[tbl_Car]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Car_tbl_Status] FOREIGN KEY([StatusID])
REFERENCES [dbo].[tbl_Status] ([StatusID])
GO
ALTER TABLE [dbo].[tbl_Car] CHECK CONSTRAINT [FK_tbl_Car_tbl_Status]
GO
ALTER TABLE [dbo].[tbl_Discount]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Discount_tbl_Status] FOREIGN KEY([StatusID])
REFERENCES [dbo].[tbl_Status] ([StatusID])
GO
ALTER TABLE [dbo].[tbl_Discount] CHECK CONSTRAINT [FK_tbl_Discount_tbl_Status]
GO
ALTER TABLE [dbo].[tbl_Invoice]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Invoice_tbl_Discount] FOREIGN KEY([DiscountID])
REFERENCES [dbo].[tbl_Discount] ([DiscountID])
GO
ALTER TABLE [dbo].[tbl_Invoice] CHECK CONSTRAINT [FK_tbl_Invoice_tbl_Discount]
GO
ALTER TABLE [dbo].[tbl_Invoice]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Invoice_tbl_Status] FOREIGN KEY([StatusID])
REFERENCES [dbo].[tbl_Status] ([StatusID])
GO
ALTER TABLE [dbo].[tbl_Invoice] CHECK CONSTRAINT [FK_tbl_Invoice_tbl_Status]
GO
ALTER TABLE [dbo].[tbl_Invoice]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Invoice_tbl_User] FOREIGN KEY([Email])
REFERENCES [dbo].[tbl_User] ([Email])
GO
ALTER TABLE [dbo].[tbl_Invoice] CHECK CONSTRAINT [FK_tbl_Invoice_tbl_User]
GO
ALTER TABLE [dbo].[tbl_InvoiceDetail]  WITH CHECK ADD  CONSTRAINT [FK_tbl_InvoiceDetail_tbl_Car] FOREIGN KEY([CarID])
REFERENCES [dbo].[tbl_Car] ([CarID])
GO
ALTER TABLE [dbo].[tbl_InvoiceDetail] CHECK CONSTRAINT [FK_tbl_InvoiceDetail_tbl_Car]
GO
ALTER TABLE [dbo].[tbl_InvoiceDetail]  WITH CHECK ADD  CONSTRAINT [FK_tbl_InvoiceDetail_tbl_Invoice] FOREIGN KEY([InvoiceID])
REFERENCES [dbo].[tbl_Invoice] ([InvoiceID])
GO
ALTER TABLE [dbo].[tbl_InvoiceDetail] CHECK CONSTRAINT [FK_tbl_InvoiceDetail_tbl_Invoice]
GO
ALTER TABLE [dbo].[tbl_User]  WITH CHECK ADD  CONSTRAINT [FK_tbl_User_tbl_Role] FOREIGN KEY([RoleID])
REFERENCES [dbo].[tbl_Role] ([RoleID])
GO
ALTER TABLE [dbo].[tbl_User] CHECK CONSTRAINT [FK_tbl_User_tbl_Role]
GO
ALTER TABLE [dbo].[tbl_User]  WITH CHECK ADD  CONSTRAINT [FK_tbl_User_tbl_Status] FOREIGN KEY([StatusID])
REFERENCES [dbo].[tbl_Status] ([StatusID])
GO
ALTER TABLE [dbo].[tbl_User] CHECK CONSTRAINT [FK_tbl_User_tbl_Status]
GO
USE [master]
GO
ALTER DATABASE [Car] SET  READ_WRITE 
GO
